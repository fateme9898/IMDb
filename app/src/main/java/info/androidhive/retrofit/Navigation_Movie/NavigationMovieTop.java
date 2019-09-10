package info.androidhive.retrofit.Navigation_Movie;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.List;

import info.androidhive.retrofit.R;
import info.androidhive.retrofit.activity.MainActivity;
import info.androidhive.retrofit.activity.MovieDetail;
import info.androidhive.retrofit.adapter.TopMovieAdapter;
import info.androidhive.retrofit.another.ItemTouchListener;
import info.androidhive.retrofit.model.Top_movie.TopMovie;
import info.androidhive.retrofit.model.Top_movie.TopMovieResponse;
import info.androidhive.retrofit.rest.ApiClient;
import info.androidhive.retrofit.rest.ApiInterface;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavigationMovieTop.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link NavigationMovieTop#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NavigationMovieTop extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private static final String TAG = MainActivity.class.getSimpleName();


    // TODO - insert your themoviedb.org API KEY here
    private final static String API_KEY = "53f93d98fdababca6efda85ba6ccc57a";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    public NavigationMovieTop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NavigationMovieTop.
     */
    // TODO: Rename and change types and number of parameters
    public static NavigationMovieTop newInstance(String param1, String param2) {
        NavigationMovieTop fragment = new NavigationMovieTop();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

       View view= inflater.inflate(R.layout.fragment_navigation_movie_top, container, false);




        if (API_KEY.isEmpty()) {
            Toast.makeText(getActivity().getApplicationContext(), "Please obtain your API KEY from " +
                    "themoviedb.org first!", Toast.LENGTH_LONG).show();


        }
        final RecyclerView recyclerView = view.findViewById(R.id.recyclerview_topmovie);
        recyclerView.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext().getApplicationContext(),
                LinearLayoutManager.VERTICAL, false));


        recyclerView.addOnItemTouchListener(new ItemTouchListener(recyclerView) {
            @Override
            public boolean onClick(RecyclerView parent, View view, int position, long id) {
                TopMovieAdapter topMovieAdapter = (TopMovieAdapter) recyclerView.getAdapter();
                TopMovie movie = TopMovieAdapter.topMovies.get(position);
                Intent intent = new Intent(getActivity(), MovieDetail.class);

                intent.putExtra("TYPE", movie.getId());
                startActivity(intent);
                return false;
            }

            @Override
            public boolean onLongClick(RecyclerView parent, View view, int position, long id) {
                return false;
            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });



        ApiInterface apiServic= ApiClient.getClient().create(ApiInterface.class);
        Call<TopMovieResponse> call=apiServic.getTopMovie(API_KEY);
        call.enqueue(new Callback<TopMovieResponse>() {
            @Override
            public void onResponse(Call <TopMovieResponse> call, Response<TopMovieResponse> response) {
                int code=response.code();

                List<TopMovie> topMovies=response.body().getResults();
                TopMovieAdapter adapter=new TopMovieAdapter(topMovies , R.layout.list_item_movie_top,getContext() );
                recyclerView.setAdapter(adapter);


                adapter.notifyDataSetChanged();


            }

            @Override
            public void onFailure(Call <TopMovieResponse> call, Throwable t) {
                Log.e(TAG, t.toString());

            }
        });

       return  view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
