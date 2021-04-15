package com.fatmanaseer.paintapp.presentation.admin.ui.workers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fatmanaseer.paintapp.R
import com.fatmanaseer.paintapp.models.worker.Worker

class WorkersFragment : Fragment(), OnWorkersActionsListener {

    private lateinit var workersViewModel: WorkersViewModel

    private lateinit var workersAdapter: WorkersAdapter

    private lateinit var noWorkerTV: AppCompatTextView
    private lateinit var workersRV: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        workersViewModel =
            ViewModelProvider(this).get(WorkersViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_workers, container, false)
        noWorkerTV = root.findViewById(R.id.noWorkersTV)
        workersRV = root.findViewById(R.id.workersRV)
        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeViewModel()
    }

    private fun observeViewModel() {
        workersViewModel.workers?.observe(viewLifecycleOwner) {
            it?.let { workers ->
                if (workers.isEmpty()) {
                    noWorkerTV.visibility = View.VISIBLE
                    workersRV.visibility = View.GONE
                } else {
                    noWorkerTV.visibility = View.GONE
                    workersAdapter = WorkersAdapter(workers, this)
                    workersRV.apply {
                        visibility = View.VISIBLE
                        layoutManager = LinearLayoutManager(this@WorkersFragment.requireContext())
                        adapter = workersAdapter
                    }
                }
            }
        }
    }

    override fun onDeleteOwner(worker: Worker) {
        workersViewModel.deleteWorker(worker.username)
    }

    override fun onActiveOwner(worker: Worker) {
        workersViewModel.activeWorker(worker)
    }
}