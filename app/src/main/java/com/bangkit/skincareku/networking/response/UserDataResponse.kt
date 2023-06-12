package com.bangkit.skincareku.networking.response

import com.google.gson.annotations.SerializedName

data class UserDataResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class Serializer(

	@field:SerializedName("allowUndefined")
	val allowUndefined: Boolean? = null
)

data class CreateTime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class FieldsProto(

	@field:SerializedName("uid")
	val uid: Uid? = null,

	@field:SerializedName("gender")
	val gender: Gender? = null,

	@field:SerializedName("skinProblem")
	val skinProblem: SkinProblem? = null,

	@field:SerializedName("allergy")
	val allergy: Allergy? = null,

	@field:SerializedName("birthDate")
	val birthDate: BirthDate? = null,

	@field:SerializedName("email")
	val email: Email? = null,

	@field:SerializedName("token")
	val token: Token? = null
)

data class UpdateTime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class SkinProblem(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class Data(

	@field:SerializedName("_createTime")
	val createTime: CreateTime? = null,

	@field:SerializedName("_fieldsProto")
	val fieldsProto: FieldsProto? = null,

	@field:SerializedName("_ref")
	val ref: Ref? = null,

	@field:SerializedName("_serializer")
	val serializer: Serializer? = null,

	@field:SerializedName("_readTime")
	val readTime: ReadTime? = null,

	@field:SerializedName("_updateTime")
	val updateTime: UpdateTime? = null
)

data class Allergy(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class Email(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class BirthDate(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class Firestore(

	@field:SerializedName("projectId")
	val projectId: String? = null
)

data class Gender(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class Converter(
	val any: Any? = null
)

data class Uid(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)

data class Path(

	@field:SerializedName("segments")
	val segments: List<String?>? = null
)

data class ReadTime(

	@field:SerializedName("_nanoseconds")
	val nanoseconds: Int? = null,

	@field:SerializedName("_seconds")
	val seconds: Int? = null
)

data class Ref(

	@field:SerializedName("_firestore")
	val firestore: Firestore? = null,

	@field:SerializedName("_converter")
	val converter: Converter? = null,

	@field:SerializedName("_path")
	val path: Path? = null
)

data class Token(

	@field:SerializedName("stringValue")
	val stringValue: String? = null,

	@field:SerializedName("valueType")
	val valueType: String? = null
)
