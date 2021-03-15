package com.zeustech.zeuskit.database.protocols

import com.couchbase.lite.Database

interface CouchbaseDatabase {
    val database: Database
}