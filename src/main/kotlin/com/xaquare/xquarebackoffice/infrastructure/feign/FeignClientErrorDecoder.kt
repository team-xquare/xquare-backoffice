package com.xaquare.xquarebackoffice.infrastructure.feign

import com.xaquare.xquarebackoffice.infrastructure.feign.exception.FeignBadRequestException
import com.xaquare.xquarebackoffice.infrastructure.feign.exception.FeignForbiddenException
import com.xaquare.xquarebackoffice.infrastructure.feign.exception.FeignServerError
import com.xaquare.xquarebackoffice.infrastructure.feign.exception.FeignUnAuthorizedException
import feign.FeignException
import feign.Response
import feign.codec.ErrorDecoder

class FeignClientErrorDecoder : ErrorDecoder {
    override fun decode(methodKey: String?, response: Response?): Exception {
        if (response!!.status() >= 400) {
            when (response.status()) {
                400 -> throw FeignBadRequestException
                401 -> throw FeignUnAuthorizedException
                403 -> throw FeignForbiddenException
                else -> throw FeignServerError
            }
        }
        return FeignException.errorStatus(methodKey, response)
    }
}
