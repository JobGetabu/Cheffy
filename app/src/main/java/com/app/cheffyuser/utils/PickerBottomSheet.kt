package com.app.cheffyuser.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.app.cheffyuser.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class PickerBottomSheet(private var pickerInterface: PickerInterface) : BottomSheetDialogFragment() {

    companion object {
        const val TAG = "PickerBottomSheet"
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val v = inflater.inflate(R.layout.sheet_picker, container, false)

        val tv_camera = v.findViewById<TextView>(R.id.tv_camera)
        val im_camera = v.findViewById<ImageButton>(R.id.im_camera)

        val tv_gallery = v.findViewById<TextView>(R.id.tv_gallery)
        val im_gallery = v.findViewById<ImageButton>(R.id.im_gallery)


        tv_camera.setOnClickListener {
            cameraPicked()
        }
        im_camera.setOnClickListener {
            cameraPicked()
        }

        tv_gallery.setOnClickListener {
            galleryPicked()
        }
        im_gallery.setOnClickListener {
            galleryPicked()
        }
        return v
    }

    private fun galleryPicked(){
        pickerInterface.picked(false)
        dismiss()
    }

    private fun cameraPicked(){
        pickerInterface.picked(true)
        dismiss()
    }
}