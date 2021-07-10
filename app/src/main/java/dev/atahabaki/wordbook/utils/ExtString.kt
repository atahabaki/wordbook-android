package dev.atahabaki.wordbook.utils

fun String.toQFS(): Triple<String, Filter, Sort> {
    var query = ""
    var sort = Sort.BY_ID_ASC
    var filter = Filter.SHOW_ALL

    val list = this.split(" ").filter { it.trim().isNotEmpty() }.toMutableList()
    var countOfDeletedEntries = 0
    val toTrash: MutableList<Int> = mutableListOf()
    for ((i,word) in list.withIndex()) {
        fun addDeleteCommand() {
            toTrash.add(i-countOfDeletedEntries)
            countOfDeletedEntries+=1
        }
        when (word) {
            Filter.SHOW_ONLY_FAV.searchQuery -> {
                filter = Filter.SHOW_ONLY_FAV
                addDeleteCommand()
            }
            Filter.SHOW_ONLY_NOT_FAV.searchQuery -> {
                filter = Filter.SHOW_ONLY_NOT_FAV
                addDeleteCommand()
            }
            Sort.BY_ID_ASC.searchQuery -> {
                sort = Sort.BY_ID_ASC
                addDeleteCommand()
            }
            Sort.BY_ID_DESC.searchQuery -> {
                sort = Sort.BY_ID_DESC
                addDeleteCommand()
            }
            Sort.BY_TITLE_ASC.searchQuery -> {
                sort = Sort.BY_TITLE_ASC
                addDeleteCommand()
            }
            Sort.BY_TITLE_DESC.searchQuery -> {
                sort = Sort.BY_TITLE_DESC
                addDeleteCommand()
            }
            Sort.BY_MEAN_ASC.searchQuery -> {
                sort = Sort.BY_MEAN_ASC
                addDeleteCommand()
            }
            Sort.BY_MEAN_DESC.searchQuery -> {
                sort = Sort.BY_MEAN_DESC
                addDeleteCommand()
            }
            Sort.BY_DATE_ASC.searchQuery -> {
                sort = Sort.BY_DATE_ASC
                addDeleteCommand()
            }
            Sort.BY_DATE_DESC.searchQuery -> {
                sort = Sort.BY_DATE_DESC
                addDeleteCommand()
            }
            Sort.BY_FAV_ASC.searchQuery -> {
                sort = Sort.BY_FAV_ASC
                addDeleteCommand()
            }
            Sort.BY_FAV_DESC.searchQuery -> {
                sort = Sort.BY_FAV_DESC
                addDeleteCommand()
            }
        }
    }
    for (index in toTrash) {
        list.removeAt(index)
    }
    query = list.joinToString(" ")

    return Triple(query, filter, sort)
}