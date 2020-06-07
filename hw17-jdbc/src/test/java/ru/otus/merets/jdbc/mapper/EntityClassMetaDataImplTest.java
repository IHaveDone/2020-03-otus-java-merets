/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import ru.otus.merets.core.model.Account;
import ru.otus.merets.core.model.User;

@DisplayName("EntityClassMetaDataImpl must ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class EntityClassMetaDataImplTest {

    @Test
    @DisplayName("identify id correctly")
    public void initGetIdTest(){
        var userEntityClassMetaData = new EntityClassMetaDataImpl<>(User.class);
        Assertions.assertEquals("id", userEntityClassMetaData.getIdField().getName());

        var accountEntityClassMetaData = new EntityClassMetaDataImpl<>(Account.class);
        Assertions.assertEquals("no", accountEntityClassMetaData.getIdField().getName());
    }

}