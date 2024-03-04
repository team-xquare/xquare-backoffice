package com.xaquare.xquarebackoffice.global.base

import java.util.UUID
import javax.persistence.Column
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class BaseUUIDEntity(
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(
        columnDefinition = "BINARY(16)",
        nullable = false
    )
    val id: UUID?
)
