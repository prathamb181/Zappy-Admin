package com.example.pkartadmin.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pkartadmin.R
import com.example.pkartadmin.databinding.ItemCategoryLayoutBinding
import com.example.pkartadmin.databinding.ItemProductLayoutBinding
import com.example.pkartadmin.model.AllProductsModel
import com.example.pkartadmin.model.CategoryModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllProductAdapter(private var context : Context, private val list : ArrayList<AllProductsModel>) : RecyclerView.Adapter<AllProductAdapter.ProductViewHolder>(){

    inner class ProductViewHolder(view: View) : RecyclerView.ViewHolder(view){

        var binding = ItemProductLayoutBinding.bind(view)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        return ProductViewHolder(LayoutInflater.from(context).inflate(R.layout.item_product_layout,parent,false))
    }

    override fun getItemCount(): Int{
        return list.size
    }
    override fun onBindViewHolder(holder: ProductViewHolder,position: Int){

        holder.binding.tvProductName.text = list[position].productName
        holder.binding.tvPrice.text = list[position].productSP
        holder.binding.tvProductId.text = list[position].productId
        Glide.with(context).load(list[position].productCoverImg).into(holder.binding.imageProduct)

        holder.binding.btnDelete.setOnClickListener{
            Firebase.firestore.collection("products")
                .document(holder.binding.tvProductId.text.toString())
                .delete().addOnSuccessListener {
                    Toast.makeText(context, "The product is deleted", Toast.LENGTH_SHORT).show()
                }
                .addOnFailureListener{
                    Toast.makeText(context, "Failed to Delete the product", Toast.LENGTH_SHORT).show()
                }
        }
    }
}