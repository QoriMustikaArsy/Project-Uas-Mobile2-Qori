package com.example.project_uas_mobile2_qori.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_uas_mobile2_qori.MovieAdapter
import com.example.project_uas_mobile2_qori.R
import com.example.project_uas_mobile2_qori.models.Movie
import com.example.project_uas_mobile2_qori.models.MovieResponse
import com.example.project_uas_mobile2_qori.services.MovieApiInterface
import com.example.project_uas_mobile2_qori.services.MovieApiService
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private val movies = arrayListOf<Movie>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rv_movies_list.layoutManager = LinearLayoutManager(this.context)
        rv_movies_list.setHasFixedSize(true)
        getMovieData { movies : List<Movie> ->
            rv_movies_list.adapter = MovieAdapter(movies)
        }
        showRecyclerView()
    }

    private fun getMovieData(callback: (List<Movie>) -> Unit){
        val apiService = MovieApiService.getInstance().create(MovieApiInterface::class.java)
        apiService.getMovieList().enqueue(object : Callback<MovieResponse> {
            override fun onFailure(call: Call<MovieResponse>, t: Throwable) {

            }

            override fun onResponse(call: Call<MovieResponse>, response: Response<MovieResponse>) {
                return callback(response.body()!!.movies)
            }

        })
    }

    private fun showRecyclerView() {
        rv_movies_list.layoutManager = LinearLayoutManager(this.context)
        rv_movies_list.adapter = MovieAdapter(movies)
    }
}