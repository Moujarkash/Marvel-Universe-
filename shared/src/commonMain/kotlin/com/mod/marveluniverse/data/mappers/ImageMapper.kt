package com.mod.marveluniverse.data.mappers

import com.mod.marveluniverse.data.dtos.ImageDto
import com.mod.marveluniverse.domain.entites.Image

fun ImageDto.toDomainEntity() = Image(
    path = path,
    extension = extension
)

fun Image.toDataDto() = ImageDto(
    path = path,
    extension = extension
)