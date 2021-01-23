package github.tinkzhang.readkeeper.common

import github.tinkzhang.readkeeper.R

enum class BookType {
    PAPER {
        override fun nameString() = "Kindle"
        override fun logoResId() = R.drawable.ic_paper_book
    },
    KINDLE {
        override fun nameString() = "Kindle"
        override fun logoResId() = R.drawable.ic_kindle
    },
    PDF {
        override fun nameString() = "PDF"
        override fun logoResId() = R.drawable.ic_pdf
    },
    GOOGLE {
        override fun nameString() = "Play Book"
        override fun logoResId() = R.drawable.ic_google_book
    },
    APPLE {
        override fun nameString() = "Apple Book"
        override fun logoResId() = R.drawable.ic_apple_book
    },
    OREILLY {
        override fun nameString() = "O'Reilly"
        override fun logoResId() = R.drawable.ic_oreilly
    },
    WECHAT {
        override fun nameString() = "微信读书"
        override fun logoResId() = R.drawable.ic_wechat
    },
    EBOOK {
        override fun nameString() = "Other e-book"
        override fun logoResId() = R.drawable.ic_ebook
    };

    abstract fun nameString(): String
    abstract fun logoResId(): Int
}