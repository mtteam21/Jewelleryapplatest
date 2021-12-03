package com.example.jewelleryapp.Retrofit;

import com.example.jewelleryapp.CustmizedProjects.Models.CustomD_Model;
import com.example.jewelleryapp.CustmizedProjects.Models.P_SpecificationModel;
import com.example.jewelleryapp.Model.AddressModel;
import com.example.jewelleryapp.Model.CategoryData;
import com.example.jewelleryapp.Model.ChildCategoryList;
import com.example.jewelleryapp.Model.DataModel;
import com.example.jewelleryapp.Model.HorizontalProductImage;
import com.example.jewelleryapp.Model.P_Details_A;
import com.example.jewelleryapp.Model.P_Details_B;
import com.example.jewelleryapp.Model.Product;
import com.example.jewelleryapp.Model.SliderItem;
import com.example.jewelleryapp.Model.StoreVariantsDetails;
import com.example.jewelleryapp.Model.Subcategory;
import com.example.jewelleryapp.Model.User;
import com.example.jewelleryapp.Model.UserLogin;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface ApiInterface {

    @POST("/jewellery/capi/p_user_login.php")
    Call<Res> login(@Body User user);

    @Headers("Content-Type: application/json;charset=utf-8")
    @POST("p_reg_user.php")
    Call<Res> register_User(@Body UserLogin jsonBody);

    @GET("p_cat_list.php")
    Call<List<CategoryData>> getCategoriesData();

    @POST("p_scat_list.php")
    Call<List<Subcategory>> getSubcategories(@Query("cid")int cid);

    @POST("p_child_c_list.php")
    Call<List<ChildCategoryList>> fetchChildCategories(@Query("cid")int id);

    @GET("p_get_products.php")
    Call<List<Product>> getAllProducts();

    @GET("p_get_slider.php")
    Call<ArrayList<SliderItem>> getAllSliders();

    @GET("p_product_images.php")
    Call<ArrayList<HorizontalProductImage>> getProductImages(@Query("id") int id);

    @GET("p_details_a.php")
    Call<ArrayList<P_Details_A>> getProductDetailsA(@Query("id") int id);

    @GET("p_details_b.php")
    Call<ArrayList<P_Details_B>> getProductDetailsB(@Query("id") int id);

    @GET("p_product_veriants_list.php")
    Call<ArrayList<CustomD_Model>> getProductVariants(@Query("id") int id);

    @GET("p_veriant_options.php")
    Call<ArrayList<P_SpecificationModel>> getProductVariantsOptions(@Query("id") int id);

    @GET("p_get_menu_details.php")
    Call<List<DataModel>> getData();

    @POST("store_products_orders.php")
    Call<Res> storeVariantsList(@Body StoreVariantsDetails storeVariantsDetails);

    @GET("get_address.php")
    Call<ArrayList<AddressModel>> getAddressList(@Query("id") int id);

    @POST("p_address_user.php")
    Call<Res> storeAddress(@Body AddressModel addressModel);

    @PUT("update_profile.php")
    Call<Res> update_Profile(@Body UserLogin user);

}
