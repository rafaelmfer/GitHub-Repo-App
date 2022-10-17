package com.rafaelmfer.githubrepo.data.local

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import java.util.Collections

object Converters {
    @TypeConverter
    fun stringToListItemRepos(data: String?): List<GitHubRepositoriesEntity.ItemEntity> {
        if (data == null) {
            return Collections.emptyList()
        }
        val listType: Type = object :
            TypeToken<List<GitHubRepositoriesEntity.ItemEntity>>() {}.type
        return Gson().fromJson(data, listType)
    }

    @TypeConverter
    fun listItemReposToString(someObjects: List<GitHubRepositoriesEntity.ItemEntity>): String {
        return Gson().toJson(someObjects)
    }
}