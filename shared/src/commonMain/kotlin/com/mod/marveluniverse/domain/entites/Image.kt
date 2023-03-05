package com.mod.marveluniverse.domain.entites

data class Image(
    val path: String,
    val extension: String
) {
    val full get(): String = "$path.$extension"
    fun getPortraitMode(size: ImageSize = ImageSize.XLARGE): String {
        val sizeValue = when (size) {
            ImageSize.SMALL -> "small"
            ImageSize.MEDIUM -> "medium"
            ImageSize.LARGE -> "xlarge"
            ImageSize.XLARGE -> "fantastic"
            ImageSize.XXLARGE -> "uncanny"
            ImageSize.XXXLARGE -> "incredible"
        }

        return "$path/portrait_$sizeValue.$extension"
    }
    fun getStandardMode(size: ImageSize = ImageSize.XLARGE): String {
        val sizeValue = when (size) {
            ImageSize.SMALL -> "small"
            ImageSize.MEDIUM -> "medium"
            ImageSize.LARGE -> "large"
            ImageSize.XLARGE -> "xlarge"
            ImageSize.XXLARGE -> "fantastic"
            ImageSize.XXXLARGE -> "amazing"
        }

        return "$path/standard_$sizeValue.$extension"
    }

    fun getLandscapeMode(size: ImageSize = ImageSize.XLARGE): String {
        val sizeValue = when (size) {
            ImageSize.SMALL -> "small"
            ImageSize.MEDIUM -> "medium"
            ImageSize.LARGE -> "large"
            ImageSize.XLARGE -> "xlarge"
            ImageSize.XXLARGE -> "amazing"
            ImageSize.XXXLARGE -> "incredible"
        }

        return "$path/landscape_$sizeValue.$extension"
    }
}

enum class ImageSize {
    SMALL,
    MEDIUM,
    LARGE,
    XLARGE,
    XXLARGE,
    XXXLARGE
}
