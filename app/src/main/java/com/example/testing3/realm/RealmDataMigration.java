package com.example.testing3.realm;

import io.realm.DynamicRealm;
import io.realm.RealmMigration;
import io.realm.RealmObjectSchema;
import io.realm.RealmSchema;

public class RealmDataMigration implements RealmMigration {
    @Override
    public void migrate(DynamicRealm realm, long oldVersion, long newVersion) {
        final RealmSchema schema = realm.getSchema();
        if (oldVersion==1)
        {
            final RealmObjectSchema userSchema = schema.get("PeopleDetailPojo");
            userSchema.addField("mob",String.class);
            oldVersion++;
        }
        if (oldVersion==2)
        {
            final RealmObjectSchema userSchema = schema.get("DataModal");
            userSchema.addField("mob",long.class);
            userSchema.addField("bloodGroup",String.class);
            oldVersion++;
        }

    }
}
