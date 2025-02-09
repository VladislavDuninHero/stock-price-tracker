package com.pet.stock_price_tracker.service.utils.uri.restore;

import java.net.URI;

public interface RestorePasswordService {
    URI generateRestorePasswordUri(String login);
}
