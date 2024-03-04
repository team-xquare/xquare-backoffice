package com.xaquare.xquarebackoffice.domain.entity

import com.xaquare.xquarebackoffice.global.base.BaseUUIDEntity
import java.util.UUID
import javax.persistence.Column
import javax.persistence.Entity

@Entity
class User (
        id: UUID? = null,

        @Column(name = "name", nullable = false)
        val name: String,

        @Column(name = "account_id", nullable = false, unique = true)
        val accountId: String,

        @Column(name = "password", nullable = false)
        val password: String,

        @Column(name = "grade", nullable = false)
        val grade: Int,

        @Column(name = "class_num", nullable = false)
        val classNum: Int,

        @Column(name = "num", nullable = false)
        val num: Int,

        @Column(name = "profile_file")
        val profile: String ? = null,
) : BaseUUIDEntity(id)