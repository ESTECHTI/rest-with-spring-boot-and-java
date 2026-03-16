Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Get-Solution([string]$errorText) {
    if ($errorText -match "Premain-Class manifest attribute") {
        return "O javaagent configurado e invalido para o JAR informado. Revise mockito-inline/mockito-core e qualquer argLine no Surefire."
    }

    if ($errorText -match "forked VM terminated without properly saying goodbye") {
        return "A JVM de testes caiu ao iniciar. Verifique argLine do Surefire, versao do Java e dependencias de agent/instrumentacao."
    }

    if ($errorText -match "Error occurred during initialization of VM") {
        return "Falha ao subir JVM de teste. Revise javaagent/argLine do Surefire e compatibilidade entre JDK e dependencias de teste."
    }

    if ($errorText -match "NullPointerException") {
        return "Verifique mocks/objetos nao inicializados no teste e se o when(...) cobre o caminho executado."
    }

    if ($errorText -match "AssertionFailedError|ComparisonFailure") {
        return "Revise o valor esperado no assert. Para links HATEOAS, valide tambem query params no href."
    }

    if ($errorText -match "PotentialStubbingProblem|UnnecessaryStubbingException") {
        return "Ajuste stubs Mockito para bater com os argumentos reais, ou remova stubs nao usados."
    }

    if ($errorText -match "ResourceNotFoundException") {
        return "Mocke o repositorio para retornar Optional.of(...) quando o teste espera sucesso."
    }

    if ($errorText -match "ambiguous") {
        return "Tipar o matcher resolve sobrecarga ambigua, por exemplo any(Link.class) em vez de any()."
    }

    return "Abra o stack trace no primeiro frame do seu codigo e alinhe mock, entrada do teste e assert com o comportamento real."
}

$workspaceRoot = Resolve-Path (Join-Path $PSScriptRoot "..")
Push-Location $workspaceRoot

try {
    $mvnOutput = & mvn "-DtrimStackTrace=false" test 2>&1 | Tee-Object -Variable capturedMavenOutput
    $mvnExit = $LASTEXITCODE
}
finally {
    Pop-Location
}

if ($mvnExit -eq 0) {
    Write-Host "`nOK: Todos os testes passaram." -ForegroundColor Green
    exit 0
}

Write-Host "`nFALHA: Existem testes com erro. Resumo com sugestoes:`n" -ForegroundColor Red

$errorLines = $capturedMavenOutput |
    Where-Object { $_ -match "\[ERROR\]|Exception|AssertionFailedError|ComparisonFailure|<<< FAILURE!|<<< ERROR!" }

if ($errorLines -and $errorLines.Count -gt 0) {
    $primaryError = ($errorLines | Select-Object -First 1).ToString().Trim()
    $solution = Get-Solution $primaryError

    Write-Host "Erro principal:$primaryError" -ForegroundColor Red
    Write-Host "Solucao:      $solution" -ForegroundColor Yellow
    Write-Host ""

    $extraLines = $errorLines | Select-Object -Skip 1 -First 5
    if ($extraLines) {
        Write-Host "Mais detalhes:" -ForegroundColor Cyan
        foreach ($line in $extraLines) {
            Write-Host "- $($line.ToString().Trim())"
        }
        Write-Host ""
    }
}

$reportDir = Join-Path $workspaceRoot "target\surefire-reports"
if (-not (Test-Path $reportDir)) {
    Write-Host "Nao foi encontrado target/surefire-reports para diagnostico." -ForegroundColor Yellow
    exit $mvnExit
}

$reportFiles = Get-ChildItem -Path $reportDir -Filter "*.txt" -File | Sort-Object LastWriteTime -Descending
if (-not $reportFiles) {
    Write-Host "Nao ha arquivos .txt em target/surefire-reports para resumo por arquivo." -ForegroundColor Yellow
    exit $mvnExit
}

$maxReports = [Math]::Min(5, $reportFiles.Count)
$selectedReports = $reportFiles | Select-Object -First $maxReports

foreach ($file in $selectedReports) {
    $content = Get-Content -Path $file.FullName -Raw
    $lines = $content -split "`r?`n"

    $failureHeader = $lines | Where-Object { $_ -match "<<< FAILURE!|<<< ERROR!" } | Select-Object -First 1
    $exceptionLine = $lines | Where-Object { $_ -match "Exception|AssertionFailedError|ComparisonFailure|Error:" } | Select-Object -First 1

    if (-not $failureHeader -and -not $exceptionLine) {
        continue
    }

    $errorSummary = if ($exceptionLine) { $exceptionLine.Trim() } else { $failureHeader.Trim() }
    $solution = Get-Solution $errorSummary

    Write-Host "Arquivo: $($file.Name)" -ForegroundColor Cyan
    if ($failureHeader) {
        Write-Host "Erro:   $($failureHeader.Trim())" -ForegroundColor Red
    }
    Write-Host "Detalhe:$errorSummary" -ForegroundColor Red
    Write-Host "Solucao:$solution" -ForegroundColor Yellow
    Write-Host ""
}

exit $mvnExit
