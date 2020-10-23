package org.monjasa;

import lombok.NonNull;
import lombok.Value;
import org.monjasa.model.Facility;

import java.util.Map;
import java.util.function.Function;

@Value
public class FacilityStrategy {

    @NonNull String name;

    @NonNull Function<Facility, Map<?, ?>> strategy;
}
