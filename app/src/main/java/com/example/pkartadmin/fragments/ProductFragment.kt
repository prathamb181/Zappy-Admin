package com.example.pkartadmin.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.pkartadmin.R
import com.example.pkartadmin.adapter.AllProductAdapter
import com.example.pkartadmin.adapter.CategoryAdapter
import com.example.pkartadmin.databinding.FragmentProductBinding
import com.example.pkartadmin.databinding.ItemProductLayoutBinding
import com.example.pkartadmin.model.AllProductsModel
import com.example.pkartadmin.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class ProductFragment : Fragment() {

    private lateinit var binding : FragmentProductBinding
    private lateinit var binding2 : ItemProductLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentProductBinding.inflate(layoutInflater)
        binding2 = ItemProductLayoutBinding.inflate(layoutInflater)
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        getData(requireContext())

        binding.floatingActionButton.setOnClickListener{
            Navigation.findNavController(it).navigate(R.id.action_productFragment_to_addProductFragment)
        }
        Log.d("deletion id",binding2.tvProductId.text.toString())

        return binding.root
    }

    private fun getData(context: Context) {
        val list = ArrayList<AllProductsModel>()
        binding.recyclerView.adapter = AllProductAdapter(context, list)
        Firebase.firestore.collection("products")
            .get().addOnSuccessListener {
                list.clear()
                for(doc in it.documents){
                    val data = doc.toObject(AllProductsModel::class.java)
                    list.add(data!!)
                }
                binding.recyclerView.adapter = AllProductAdapter(context,list)
            }
    }

}