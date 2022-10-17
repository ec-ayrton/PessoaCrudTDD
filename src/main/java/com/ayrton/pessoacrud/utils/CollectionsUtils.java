package com.ayrton.pessoacrud.Utils;

import java.util.List;
import java.util.Objects;

public class CollectionsUtils {

        public static boolean isNullOrEmpty(List<?> list) {
            return Objects.isNull(list) || list.isEmpty();
        }


}
