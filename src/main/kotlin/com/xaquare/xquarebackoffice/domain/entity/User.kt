package com.xaquare.xquarebackoffice.domain.entity

import java.util.UUID
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class User (
        @Id
        val id: UUID,
)