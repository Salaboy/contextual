package org.contextual.api.utils;

import java.util.UUID;

/**
 * Created by msalatino on 13/03/2017.
 */
public class IdGenerator {

    public static String generateIdForEntity(String entity){
        return entity + "-" + UUID.randomUUID().toString().substring(0, 8);
    }

}
