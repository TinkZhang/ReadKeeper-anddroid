package github.tinkzhang.readkeeper.reading

import android.os.Bundle
import android.text.Html
import android.text.Html.FROM_HTML_MODE_COMPACT
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.navGraphViewModels
import com.afollestad.materialdialogs.LayoutMode
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.bottomsheets.BasicGridItem
import com.afollestad.materialdialogs.bottomsheets.BottomSheet
import com.afollestad.materialdialogs.bottomsheets.gridItems
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.google.android.material.textfield.TextInputEditText
import github.tinkzhang.readkeeper.R
import github.tinkzhang.readkeeper.common.BookType
import kotlinx.android.synthetic.main.detail_progress_cardview.*
import kotlinx.android.synthetic.main.fragment_reading_detail.*

class ReadingDetailFragment : Fragment() {
    private lateinit var readingBook: ReadingBook
    val viewModel: ReadingViewModel by navGraphViewModels(R.id.mobile_navigation) {
        ReadingViewModelFactory(this.requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        readingBook = viewModel.selectedBook!!
        return inflater.inflate(R.layout.fragment_reading_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        detail_title.text = viewModel.selectedBook?.title
        configProgressView(readingBook.pages, readingBook.progress)
    }

    private fun configProgressView(pages: Int, progress: Int) {
        detailProgress.iconImageResource = R.drawable.ic_kindle
        detailProgress.max = pages.toFloat()
        detailProgress.progress = progress.toFloat()

        val progressHtmlString = getString(R.string.progress_html, progress, pages)
        detailProgressTextView.text = Html.fromHtml(progressHtmlString, FROM_HTML_MODE_COMPACT)
        detailProgressCardView.setOnClickListener {
            MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show outer@ {
                title(R.string.update_progress)
                cancelOnTouchOutside(false)
                cancelable(false)
                customView(R.layout.dialog_progress_update, scrollable = true, horizontalPadding = true)
                this.getCustomView().findViewById<TextInputEditText>(R.id.progressEditText).apply {
                    setText(progress.toString())
                    requestFocus()
                }
                this.getCustomView().findViewById<TextInputEditText>(R.id.bookPageEditText).setText(pages.toString())
                this.getCustomView().findViewById<CardView>(R.id.bookTypeCardView).setOnClickListener {

                    val bookTypeList = BookType.values().map { BasicGridItem(it.logoResId(), it.nameString()) }

                    MaterialDialog(this@ReadingDetailFragment.requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        gridItems(bookTypeList) { _, index, item ->
                            when (item.iconRes) {
                                R.drawable.ic_paper_book -> readingBook.type = BookType.PAPER
                                R.drawable.ic_kindle -> readingBook.type = BookType.KINDLE
                                R.drawable.ic_pdf -> readingBook.type = BookType.PDF
                                R.drawable.ic_google_book -> readingBook.type = BookType.GOOGLE
                                R.drawable.ic_apple_book -> readingBook.type = BookType.APPLE
                                R.drawable.ic_wechat -> readingBook.type = BookType.WECHAT
                                R.drawable.ic_oreilly -> readingBook.type = BookType.OREILLY
                                R.drawable.ic_ebook -> readingBook.type = BookType.EBOOK
                            }
                            this@outer.getCustomView().findViewById<ImageView>(R.id.bookTypeEditImageView)
                                    .setImageResource(readingBook.type?.logoResId() ?: R.drawable.ic_unknown)
                        }
                        positiveButton(R.string.done)
                    }
                }
                this.getCustomView().findViewById<ImageView>(R.id.bookTypeEditImageView)
                        .setImageResource(readingBook.type?.logoResId() ?: R.drawable.ic_unknown)
                positiveButton(R.string.update) { dialog ->
                    // Pull the password out of the custom view when the positive button is pressed
//                    val passwordInput: EditText = dialog.getCustomView()
//                            .findViewById(R.id.password)
                }
                negativeButton(android.R.string.cancel)
            }
        }
    }


}