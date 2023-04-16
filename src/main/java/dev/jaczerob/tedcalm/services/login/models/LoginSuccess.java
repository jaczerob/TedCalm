package dev.jaczerob.tedcalm.services.login.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.quarkus.runtime.annotations.RegisterForReflection;

@RegisterForReflection
public enum LoginSuccess {
    @JsonProperty("true")
    TRUE,

    @JsonProperty("false")
    FALSE,

    @JsonProperty("delayed")
    DELAYED,

    @JsonProperty("partial")
    PARTIAL
}
