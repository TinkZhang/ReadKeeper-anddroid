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
import androidx.lifecycle.Observer
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
    val viewModel: ReadingViewModel by navGraphViewModels(R.id.mobile_navigation) {
        ReadingViewModelFactory(this.requireActivity().application)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_reading_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.selectedBook.observe(viewLifecycleOwner, Observer {
            detail_title.text = it.title
        })
        configProgressView()
    }

    private fun configProgressView() {

        viewModel.selectedBook.observe(viewLifecycleOwner, Observer {
            detailProgress.max = it.pages.toFloat()
            detailProgress.progress = it.progress.toFloat()
            detailProgress.iconImageResource = it.type?.logoResId() ?: R.drawable.ic_unknown

            val progressHtmlString = getString(R.string.progress_html, it.progress, it.pages)
            detailProgressTextView.text = Html.fromHtml(progressHtmlString, FROM_HTML_MODE_COMPACT)
        })

        detailProgressCardView.setOnClickListener {
            MaterialDialog(requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show outer@ {
                title(R.string.update_progress)
                cancelOnTouchOutside(false)
                cancelable(false)
                customView(R.layout.dialog_progress_update, scrollable = true, horizontalPadding = true)
                val pageText = this.getCustomView().findViewById<TextInputEditText>(R.id.bookPageEditText)
                val progressText = this.getCustomView().findViewById<TextInputEditText>(R.id.progressEditText)
                progressText.apply {
                    setText(viewModel.selectedBook.value?.progress.toString())
                    requestFocus()
                }
                pageText.setText(viewModel.selectedBook.value?.pages.toString())
                this.getCustomView().findViewById<CardView>(R.id.bookTypeCardView).setOnClickListener {

                    val bookTypeList = BookType.values().map { BasicGridItem(it.logoResId(), it.nameString()) }

                    MaterialDialog(this@ReadingDetailFragment.requireContext(), BottomSheet(LayoutMode.WRAP_CONTENT)).show {
                        gridItems(bookTypeList) { _, index, item ->
                            val readingBook = viewModel.selectedBook.value
                            when (item.iconRes) {
                                R.drawable.ic_paper_book -> readingBook?.type = BookType.PAPER
                                R.drawable.ic_kindle -> readingBook?.type = BookType.KINDLE
                                R.drawable.ic_pdf -> readingBook?.type = BookType.PDF
                                R.drawable.ic_google_book -> readingBook?.type = BookType.GOOGLE
                                R.drawable.ic_apple_book -> readingBook?.type = BookType.APPLE
                                R.drawable.ic_wechat -> readingBook?.type = BookType.WECHAT
                                R.drawable.ic_oreilly -> readingBook?.type = BookType.OREILLY
                                R.drawable.ic_ebook -> readingBook?.type = BookType.EBOOK
                            }
                            this@outer.getCustomView().findViewById<ImageView>(R.id.bookTypeEditImageView)
                                    .setImageResource(readingBook?.type?.logoResId() ?: R.drawable.ic_unknown)
                        }
                        positiveButton(R.string.done)
                    }
                }
                this.getCustomView().findViewById<ImageView>(R.id.bookTypeEditImageView)
                        .setImageResource(viewModel.selectedBook.value?.type?.logoResId() ?: R.drawable.ic_unknown)
                positiveButton(R.string.update) { dialog ->
                    viewModel.selectedBook.postValue(viewModel.selectedBook.value.apply {
                        this?.progress = progressText.text.toString().toInt()
                        this?.pages = pageText.text.toString().toInt()
                    })
                    // Pull the password out of the custom view when the positive button is pressed
//                    val passwordInput: EditText = dialog.getCustomView()
//                            .findViewById(R.id.password)
                }
                negativeButton(android.R.string.cancel)
            }
        }
    }


}