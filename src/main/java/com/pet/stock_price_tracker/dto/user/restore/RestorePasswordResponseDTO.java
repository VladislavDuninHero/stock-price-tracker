package com.pet.stock_price_tracker.dto.user.restore;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.net.URI;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RestorePasswordResponseDTO {
    private String message;
    private URI uri;
}
