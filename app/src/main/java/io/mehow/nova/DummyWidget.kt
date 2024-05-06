package io.mehow.nova

import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetManager.OPTION_APPWIDGET_SIZES
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.os.Bundle
import android.util.SizeF
import android.widget.RemoteViews
import android.widget.RemoteViews.RemoteCollectionItems
import androidx.core.os.BundleCompat

class DummyWidget : AppWidgetProvider() {
    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray,
    ) {
        appWidgetIds.forEach { id ->
            val views = RemoteViews(context.packageName, R.layout.dummy_widget).apply {
                setRemoteAdapter(R.id.list, createItems(context, 4))
            }
            appWidgetManager.updateAppWidget(id, views)
        }
    }

    override fun onAppWidgetOptionsChanged(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int,
        newOptions: Bundle?,
    ) {
        val sizes = newOptions?.let { BundleCompat.getParcelableArrayList(it, OPTION_APPWIDGET_SIZES, SizeF::class.java) }
        if (sizes.isNullOrEmpty()) {
            return
        }
        val remoteViews = RemoteViews(sizes.associateWith { createRemoteViews(context, it) })
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews)
    }

    private fun createRemoteViews(context: Context, sizeF: SizeF): RemoteViews {
        val fittingItemCount = (sizeF.height / 48).toInt()
        return RemoteViews(context.packageName, R.layout.dummy_widget).apply {
            setRemoteAdapter(R.id.list, createItems(context, fittingItemCount))
        }
    }

    private fun createItems(context: Context, count: Int) = RemoteCollectionItems.Builder()
        .let { builder ->
            var currentBuilder = builder
            repeat(count) { index ->
                val views = RemoteViews(context.packageName, R.layout.dummy_widget_item).apply {
                    setTextViewText(R.id.text, "Item: $index")
                }
                currentBuilder = currentBuilder.addItem(index.toLong(), views)
            }
            currentBuilder
        }
        .setViewTypeCount(1)
        .build()
}
