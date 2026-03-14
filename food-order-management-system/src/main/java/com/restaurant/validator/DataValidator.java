package com.restaurant.validator;

import java.util.List;

public interface DataValidator<T> {
    List<String> validate(T data);
}
