package com.codegeniuses.estetikin.ui.modul

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codegeniuses.estetikin.R
import com.codegeniuses.estetikin.data.local.Module
import com.codegeniuses.estetikin.databinding.FragmentModulBinding
import com.codegeniuses.estetikin.factory.ViewModelFactory
import com.codegeniuses.estetikin.helper.LoadingHandler
import com.codegeniuses.estetikin.model.response.module.DataItem
import com.codegeniuses.estetikin.model.result.Result
import com.codegeniuses.estetikin.ui.MainActivity
import com.codegeniuses.estetikin.ui.moduleDetail.ModuleDetailActivity

class ModulFragment : Fragment(), LoadingHandler {
    private var _binding: FragmentModulBinding? = null
    private val binding get() = _binding!!
    private lateinit var factory: ViewModelFactory
    private val moduleViewModel: ModuleViewModel by viewModels { factory }
    private val adapter = ModuleAdapter()
    private var isRefreshing = false
    private lateinit var rvModule: RecyclerView
    private val moduleList = ArrayList<Module>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentModulBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val layoutManager = LinearLayoutManager(requireContext())
        binding.rvItemModule.layoutManager = layoutManager

        swipeRefresh()
    }

    override fun onResume() {
        super.onResume()
        (activity as? MainActivity)?.setActionBarTitle(getString(R.string.title_module))
        val bottomNavigation: CoordinatorLayout = requireActivity().findViewById(R.id.bottom)
        bottomNavigation.visibility = View.VISIBLE

        swipeRefresh()
        setupViewModel()
        setupModule1()
        setupAction()
    }

    private fun setupModule() {
        isRefreshing = true
        moduleViewModel.getAllModule().observe(requireActivity()) { result ->
            when (result) {
                is Result.Loading -> {
                    loadingHandler(true)
                }
                is Result.Error -> {
                    loadingHandler(false)
                    Toast.makeText(
                        requireContext(),
                        "Failed to fetch module",
                        Toast.LENGTH_SHORT
                    ).show()
                }
                is Result.Success -> {
                    loadingHandler(false)
                    adapter.setModuleData(result.data.data)
                }
            }
        }
    }

    private fun setupAction() {
        adapter.setOnItemClickCallback(object : ModuleAdapter.OnItemClickCallBack {
            override fun onItemClicked(data: DataItem) {
                showSelectedModule(data)
            }
        })
    }

    private fun showSelectedModule(data: DataItem) {
        val intent = Intent(requireContext(), ModuleDetailActivity::class.java)
        intent.putExtra("module", data)
        startActivity(intent)
    }

    private fun setupViewModel() {
        factory = ViewModelFactory.getInstance(requireContext())
    }

    private fun setupModule1(){
        rvModule = binding.rvItemModule
        rvModule.setHasFixedSize(true)

        moduleList.addAll(getModuleList())
        showRecycleList()
    }

    private fun getModuleList(): ArrayList<Module> {
        val moduleTitle = resources.getStringArray(R.array.module_title)
        val moduleIcon = resources.obtainTypedArray(R.array.module_icon)
        val moduleList = ArrayList<Module>()
        for (i in moduleTitle.indices){
            val modules = Module(moduleIcon.getResourceId(i, -1), moduleTitle[i])
            moduleList.add(modules)
        }
        return moduleList
    }

    private fun showRecycleList(){
        rvModule.layoutManager = LinearLayoutManager(requireContext())
        val listModuleAdapter = ModuleLocalAdapter(moduleList)
        rvModule.adapter = listModuleAdapter
    }
    private fun swipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener {
            setupModule1()
        }
    }

    override fun loadingHandler(isLoading: Boolean) {
        if (isLoading) {
            binding.loadingAnimation.visibility = View.VISIBLE
        } else {
            binding.loadingAnimation.visibility = View.GONE
            if (isRefreshing) {
                binding.swipeRefresh.isRefreshing = false
                isRefreshing = false
            }
        }
    }
}

