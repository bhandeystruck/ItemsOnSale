package com.example.booksonsale.ui.fragments


import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.booksonsale.R
import com.example.booksonsale.firestore.FirestoreClass
import com.example.booksonsale.models.Product
import com.example.booksonsale.ui.activities.CartListActivity
import com.example.booksonsale.ui.activities.ProductDetailsActivity
import com.example.booksonsale.ui.activities.SettingsActivity
import com.example.booksonsale.ui.adapters.DashboardItemsListAdapter
import com.example.booksonsale.utils.Constants
import kotlinx.android.synthetic.main.fragment_dashboard.*

class DashboardFragment : BaseFragment() {


  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setHasOptionsMenu(true)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View? {

    val root = inflater.inflate(R.layout.fragment_dashboard, container, false)
    return root
  }

  override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
    inflater.inflate(R.menu.dashboard_menu, menu)
    super.onCreateOptionsMenu(menu, inflater)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    val id = item.itemId

    when (id){
      R.id.action_settings -> {
        startActivity(Intent(activity, SettingsActivity::class.java))
        return true
      }
      R.id.action_cart->{
        startActivity(Intent(activity, CartListActivity::class.java))
        return true

      }
    }

    return super.onOptionsItemSelected(item)
  }

  /**
   * A function to get the dashboard items list from cloud firestore.
   */
  private fun getDashboardItemsList() {
    // Show the progress dialog.
    showProgressDialog(resources.getString(R.string.please_wait))

    FirestoreClass().getDashboardItemsList(this@DashboardFragment)
  }


  override fun onResume() {
    super.onResume()

    getDashboardItemsList()
  }

  /**
   * A function to get the success result of the dashboard items from cloud firestore.
   *
   * @param dashboardItemsList
   */
  fun successDashboardItemsList(dashboardItemsList: ArrayList<Product>) {

    // Hide the progress dialog.
    hideProgressDialog()

    if (dashboardItemsList.size > 0) {

      rv_dashboard_items.visibility = View.VISIBLE
      tv_no_dashboard_items_found.visibility = View.GONE

      rv_dashboard_items.layoutManager = GridLayoutManager(activity, 2)
      rv_dashboard_items.setHasFixedSize(true)

      val adapter = DashboardItemsListAdapter(requireActivity(), dashboardItemsList)
      rv_dashboard_items.adapter = adapter

      adapter.setOnClickListener(object :
        DashboardItemsListAdapter.OnClickListener {
        override fun onClick(position: Int, product: Product) {

          val intent = Intent(context, ProductDetailsActivity::class.java)
          intent.putExtra(Constants.EXTRA_PRODUCT_ID, product.product_id)
          intent.putExtra(Constants.EXTRA_PRODUCT_OWNER_ID, product.user_id)
          startActivity(intent)
        }
      })
      // END
    } else {
      rv_dashboard_items.visibility = View.GONE
      tv_no_dashboard_items_found.visibility = View.VISIBLE
    }
  }
}