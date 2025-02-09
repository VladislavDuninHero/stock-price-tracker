package com.pet.stock_price_tracker.dto.user.restore;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRestorePasswordDTO {

    @NotNull
    @NotEmpty
    private String newPassword;
}
