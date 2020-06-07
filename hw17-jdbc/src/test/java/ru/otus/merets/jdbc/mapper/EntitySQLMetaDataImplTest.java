/*
 * Copyright (c) 2020 Artem Merets
 */

package ru.otus.merets.jdbc.mapper;

import org.junit.jupiter.api.*;
import ru.otus.merets.core.model.User;

@DisplayName("EntitySQLMetaDataImpl must ")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class EntitySQLMetaDataImplTest {
    private EntitySQLMetaDataImpl sqlMetaData;

    @BeforeAll
    public void init(){
        sqlMetaData = new EntitySQLMetaDataImpl( User.class );
    }

    @Test
    @DisplayName("generate selectAll correctly")
    public void selectAllTest(){
        Assertions.assertEquals( "SELECT * FROM user", sqlMetaData.getSelectAllSql() );
    }

    @Test
    @DisplayName("generate selectById correctly")
    public void selectByIdTest(){
        Assertions.assertEquals( "SELECT * FROM user WHERE id=?", sqlMetaData.getSelectByIdSql() );
    }

    @Test
    @DisplayName("generate insert correctly")
    public void insertTest(){
        Assertions.assertEquals( "INSERT INTO user(id,name,age) VALUES(?,?,?)", sqlMetaData.getInsertSql() );
    }

    @Test
    @DisplayName("generate update correctly")
    public void updateTest(){
        Assertions.assertEquals( "UPDATE user SET name=?,age=? WHERE id=?", sqlMetaData.getUpdateSql() );
    }
}
