package com.bytezaptech.jawlineexercise_faceyoga.ui.history

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.navArgs
import com.bytezaptech.jawlineexercise_faceyoga.R
import com.bytezaptech.jawlineexercise_faceyoga.databinding.FragmentArticleDetailsSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ArticleDetailsSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentArticleDetailsSheetBinding
    val args: ArticleDetailsSheetFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater,
            R.layout.fragment_article_details_sheet, container, false)

        binding.titleTv.text = args.data?.title
        binding.descTv.text = args.data?.description

        binding.gif.setImageResource(args.data?.image!!)
        return binding.root
    }
}