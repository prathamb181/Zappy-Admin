package com.example.pkartadmin.adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View.GONE
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.pkartadmin.databinding.AllOrdersItemLayoutBinding
import com.example.pkartadmin.model.AllOrderModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class AllOrderAdapter(val list : ArrayList<AllOrderModel>, val context: Context)
    : RecyclerView.Adapter<AllOrderAdapter.AllOrderViewHolder>(){

    inner class AllOrderViewHolder(val binding : AllOrdersItemLayoutBinding)
        :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllOrderViewHolder {
        return AllOrderViewHolder(
            AllOrdersItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: AllOrderViewHolder, position: Int) {
        holder.binding.productTitle.text = list[position].name
        holder.binding.productPrice.text = list[position].price
        holder.binding.cancelButton.setOnClickListener{
            holder.binding.proceedButton.visibility = GONE
            updateStatus("Canceled",list[position].orderId!!)
        }
        when(list[position].status){
            "Ordered" -> {
                holder.binding.proceedButton.text = "Dispatched"
                holder.binding.proceedButton.setOnClickListener{
                    updateStatus("Dispatched",list[position].orderId!!)
                }
            }
            "Dispatched" -> {
                holder.binding.proceedButton.text = "Delivered"
                holder.binding.proceedButton.setOnClickListener{
                    updateStatus("Delivered",list[position].orderId!!)
                }
            }
            "Delivered" -> {
                holder.binding.cancelButton.visibility = GONE
                holder.binding.proceedButton.isEnabled = false
                holder.binding.proceedButton.setBackgroundColor(Color.DKGRAY)
                holder.binding.proceedButton.setTextColor(Color.WHITE)
                holder.binding.proceedButton.text = "Already Delivered"
//                holder.binding.proceedButton.setOnClickListener{
//                    updateStatus("Canceled",list[position].orderId!!)
//                }
            }
            "Canceled" -> {
                holder.binding.proceedButton.visibility = GONE
                holder.binding.cancelButton.isEnabled = false
                holder.binding.cancelButton.setBackgroundColor(Color.DKGRAY)
                holder.binding.cancelButton.setTextColor(Color.WHITE)

            }
        }
    }

    private fun updateStatus(str : String, doc: String){
        val data = hashMapOf<String,Any>()
        data["status"] = str
        Firebase.firestore.collection("allOrders")
            .document(doc).update(data).addOnSuccessListener {
                Toast.makeText(context, "Status Updated", Toast.LENGTH_SHORT).show()
            }
    }
}