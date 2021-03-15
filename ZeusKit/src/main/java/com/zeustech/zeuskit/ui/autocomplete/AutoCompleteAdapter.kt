package com.zeustech.zeuskit.ui.autocomplete

import android.content.Context
import android.text.Spannable
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.AdapterView.OnItemClickListener
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Filter
import androidx.annotation.ColorInt
import androidx.annotation.FontRes
import androidx.annotation.LayoutRes
import com.zeustech.zeuskit.ui.other.AdapterClickListener
import com.zeustech.zeuskit.ui.span.FontCache.getTypeface
import com.zeustech.zeuskit.ui.span.SpanTypeface
import java.util.*
import kotlin.collections.ArrayList

abstract class AutoCompleteAdapter<T : DescriptionModel>(
    context: Context,
    @LayoutRes private val resource: Int,
    @FontRes private val highlightFont: Int,
    @ColorInt private val highlightColor: Int
) : ArrayAdapter<T>(context, 0), OnItemClickListener {

    var dataSet: List<T> = ArrayList()
        set(value) {
            if (field == value) return
            field = value
            notifyAdapter(value)
        }

    private var filterPattern: String? = null
    private var clickListener: AdapterClickListener<T>? = null

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var str: SpannableString? = null
        getItem(position)?.let { model ->
            filterPattern?.let { pattern ->
                val description = model.getText().toLowerCase(Locale.ROOT).trim { it <= ' ' }
                val comparableText = description.toLowerCase(Locale.ROOT).trim { it <= ' ' }
                val index = comparableText.indexOf(pattern)
                if (index != -1) {
                    str = SpannableString(description)
                    getTypeface(parent.context, highlightFont)?.let {
                        str?.setSpan(
                            SpanTypeface(it),
                            index,
                            index + pattern.length,
                            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                        )
                    }
                    str?.setSpan(
                        ForegroundColorSpan(highlightColor),
                        index,
                        index + pattern.length,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }
            }
        }
        val cv: View = convertView ?: LayoutInflater.from(context).inflate(resource, parent, false)
        onBindView(getItem(position), str, cv)
        return cv
    }

    override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        val listener = clickListener ?: return
        val model = getItem(position) ?: return
        view?.let { listener.onClick(it, model, position) }
    }

    protected abstract fun onBindView(
        model: T?,
        text: SpannableString?,
        viewHolder: View
    )

    private fun notifyAdapter(dataSet: List<T>) {
        clear()
        addAll(dataSet)
        notifyDataSetChanged()
    }

    fun setOnItemClickListener(
        tv: AutoCompleteTextView,
        clickListener: AdapterClickListener<T>
    ) {
        tv.onItemClickListener = this
        this.clickListener = clickListener
    }

    private fun getSuggestions(filterPattern: String): List<T> {
        return dataSet.filter { element ->
            element.getText()
                .toLowerCase(Locale.ROOT)
                .contains(filterPattern.toLowerCase(Locale.ROOT)
                    .trim { it <= ' ' })
        }
    }

    fun getFirstSuggestion(filterPattern: String): T? {
        val suggestions = getSuggestions(filterPattern)
        return if (suggestions.isNotEmpty()) suggestions[0] else null
    }

    override fun getFilter(): Filter {
        return object : Filter() {

            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                val results = FilterResults()
                var suggestions: List<T> = ArrayList()
                if (dataSet.isNotEmpty()) {
                    val filterPattern = charSequence?.toString()?.toLowerCase(Locale.ROOT)?.trim { it <= ' ' } ?: ""
                    this@AutoCompleteAdapter.filterPattern = filterPattern
                    suggestions = getSuggestions(filterPattern)
                }
                results.values = suggestions
                results.count = suggestions.size
                return results
            }

            override fun publishResults(charSequence: CharSequence?, filterResults: FilterResults?) {
                val result = filterResults?.values as? List<T> ?: ArrayList()
                notifyAdapter(result)
            }

            override fun convertResultToString(resultValue: Any?): CharSequence = (resultValue as? T)?.getText() ?: ""

        }
    }

}