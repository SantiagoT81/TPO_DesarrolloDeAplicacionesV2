package ar.edu.uade.c12024.tpo.UI

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import ar.edu.uade.c12024.tpo.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.time.LocalDateTime

class RegistroFallidoFragment : BottomSheetDialogFragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.registrofallido_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val btnOk: Button = view.findViewById(R.id.btnContinuar)

        btnOk.setOnClickListener {
            Toast.makeText(requireContext(), LocalDateTime.now().toString(), Toast.LENGTH_SHORT).show()
            dismiss()
        }
    }}