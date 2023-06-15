package com.bangkit.skincareku.networking.response

import com.google.gson.annotations.SerializedName

data class ArticleResponse(

	@field:SerializedName("total_hits")
	val totalHits: Int? = null,

	@field:SerializedName("user_input")
	val userInput: UserInput? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("total_pages")
	val totalPages: Int? = null,

	@field:SerializedName("articles")
	val articles: List<ArticlesItem?>? = null,

	@field:SerializedName("status")
	val status: String? = null,

	@field:SerializedName("page_size")
	val pageSize: Int? = null
)

data class ArticlesItem(

	@field:SerializedName("summary")
	val summary: String? = null,

	@field:SerializedName("country")
	val country: String? = null,

	@field:SerializedName("author")
	val author: String? = null,

	@field:SerializedName("link")
	val link: String? = null,

	@field:SerializedName("language")
	val language: String? = null,

	@field:SerializedName("media")
	val media: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("_score")
	val score: Any? = null,

	@field:SerializedName("clean_url")
	val cleanUrl: String? = null,

	@field:SerializedName("published_date_precision")
	val publishedDatePrecision: String? = null,

	@field:SerializedName("rights")
	val rights: String? = null,

	@field:SerializedName("is_opinion")
	val isOpinion: Boolean? = null,

	@field:SerializedName("rank")
	val rank: Int? = null,

	@field:SerializedName("topic")
	val topic: String? = null,

	@field:SerializedName("twitter_account")
	val twitterAccount: String? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("excerpt")
	val excerpt: String? = null,

	@field:SerializedName("published_date")
	val publishedDate: String? = null,

	@field:SerializedName("authors")
	val authors: String? = null
)

data class UserInput(

	@field:SerializedName("search_in")
	val searchIn: List<String?>? = null,

	@field:SerializedName("sources")
	val sources: Any? = null,

	@field:SerializedName("not_sources")
	val notSources: List<Any?>? = null,

	@field:SerializedName("countries")
	val countries: List<String?>? = null,

	@field:SerializedName("sort_by")
	val sortBy: String? = null,

	@field:SerializedName("not_lang")
	val notLang: Any? = null,

	@field:SerializedName("from_rank")
	val fromRank: Any? = null,

	@field:SerializedName("q")
	val q: String? = null,

	@field:SerializedName("size")
	val size: Int? = null,

	@field:SerializedName("published_date_precision")
	val publishedDatePrecision: Any? = null,

	@field:SerializedName("ranked_only")
	val rankedOnly: String? = null,

	@field:SerializedName("topic")
	val topic: Any? = null,

	@field:SerializedName("from")
	val from: String? = null,

	@field:SerializedName("to")
	val to: Any? = null,

	@field:SerializedName("page")
	val page: Int? = null,

	@field:SerializedName("lang")
	val lang: Any? = null,

	@field:SerializedName("to_rank")
	val toRank: Any? = null,

	@field:SerializedName("not_countries")
	val notCountries: Any? = null
)
