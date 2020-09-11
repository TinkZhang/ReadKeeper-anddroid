package github.tinkzhang.readkeeper.search

import github.tinkzhang.readkeeper.common.ui.BookCardInteraction
import github.tinkzhang.readkeeper.search.model.SearchBook

interface SearchCardInteraction : BookCardInteraction<SearchBook> {
    fun onAddButtonClicked(book: SearchBook)
}