package br.com.estech.controllers.docs;

import br.com.estech.data.dto.BookDTO;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.MediaType;

import java.util.List;

public interface BookControllerDocs {

    @Operation(summary = "Find All Books",
        description = "Find All Books",
        tags = {"Books"},
        responses = {
                @ApiResponse(
                        description = "Success",
                        responseCode = "200",
                        content = {
                                @Content(
                                        mediaType = MediaType.APPLICATION_JSON_VALUE,
                                        array = @ArraySchema(schema = @Schema(implementation = BookDTO.class))
                                )
                        }
                ),
                @ApiResponse(description = "No content", responseCode = "204", content = @Content),
                @ApiResponse(description = "Bad Request", responseCode = "400", content = @Content),
                @ApiResponse(description = "Unauthorized", responseCode = "401", content = @Content),
                @ApiResponse(description = "No found", responseCode = "404", content = @Content),
                @ApiResponse(description = "Internal Server Error", responseCode = "500", content = @Content),
        }
    )
    List<BookDTO> findAll();
}
