package com.ayrton.pessoacrud.utils;

import java.util.List;
import java.util.Objects;

public class CollectionsUtils {

        private CollectionsUtils(){}
        public static boolean isNullOrEmpty(List<?> list) {
            return Objects.isNull(list) || list.isEmpty();
        }


}
