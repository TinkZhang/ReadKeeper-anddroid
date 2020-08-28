package github.tinkzhang.readkeeper.common

interface HasProgressIndicator {
    fun showProgressIndicator()
    fun hideProgressIndicator()
    fun showBlockingProgressIndicator()
    fun hideBlockingProgressIndicator()
}