package com.fatmanaseer.decoration.presentation.admin.ui.owners

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.decoration.R
import com.fatmanaseer.decoration.models.owner.Owner

class OwnersFragment : Fragment(), OnOwnersActionsListener {

    private lateinit var ownersViewModel: OwnersViewModel

    private lateinit var ownersAdapter: OwnersAdapter

    private lateinit var noOwnersTV: AppCompatTextView
    private lateinit var ownersRV: RecyclerView


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        ownersViewModel =
            ViewModelProvider(this).get(OwnersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_owners, container, false)
        ownersRV = root.findViewById(R.id.ownersRV)
        noOwnersTV = root.findViewById(R.id.noOwnersTV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        ownersViewModel.owners?.observe(viewLifecycleOwner) {
            it?.let { owners ->
                if (owners.isEmpty()) {
                    noOwnersTV.visibility = View.VISIBLE
                    ownersRV.visibility = View.GONE
                } else {
                    noOwnersTV.visibility = View.GONE
                    ownersAdapter = OwnersAdapter(owners, this)
                    ownersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager = LinearLayoutManager(this@OwnersFragment.requireContext())
                        adapter = ownersAdapter
                    }
                }
            }
        }
    }

    override fun onDeleteOwner(owner: Owner) {
        ownersViewModel.deleteOwner(owner.username)
    }

    override fun onActiveOwner(owner: Owner) {
        ownersViewModel.activeOwner(owner)
    }
}