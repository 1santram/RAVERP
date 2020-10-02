package com.rav.raverp.ui.fragment;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.rav.raverp.R;
import com.rav.raverp.data.adapter.PlotAvailableListAdapter;
import com.rav.raverp.data.interfaces.DialogActionCallback;
import com.rav.raverp.data.interfaces.ListItemClickListener;
import com.rav.raverp.data.model.api.ApiResponse;
import com.rav.raverp.data.model.api.GetBlock;
import com.rav.raverp.data.model.api.GetProject;
import com.rav.raverp.data.model.api.PlotAvailable;
import com.rav.raverp.databinding.DialogPlotFilterBinding;
import com.rav.raverp.network.ApiClient;
import com.rav.raverp.network.ApiClientlocal;
import com.rav.raverp.network.ApiHelper;
import com.rav.raverp.ui.PlotAvailabilityActivityDetails;
import com.rav.raverp.utils.ViewUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class PloastAvailabilityFragment extends Fragment {
    private ApiHelper apiHelper, apiHelperlocal;
    private View view;
    private RecyclerView recyclerPloatList;
    private PlotAvailableListAdapter plotAvailableListAdapter;
    private boolean isDialogHided;
    private Dialog filterDialog;
    private Spinner project_name_spinner, block_name_spinner;
    String ProjectId,BlockId;
    TextView  no_records_text_view;


    private ListItemClickListener listItemClickListener = new ListItemClickListener() {
        @Override
        public void onItemClicked(int itemPosition) {
            PlotAvailable plotAvailable =
                    plotAvailableListAdapter.getPlotAvailables().get(itemPosition);

            Intent intent = new Intent(getActivity(), PlotAvailabilityActivityDetails.class);
            intent.putExtra("ploatData", plotAvailable);
            startActivity(intent);

        }
    };


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.activity_recycler_view, container, false);
        apiHelper = ApiClient.getClient().create(ApiHelper.class);

        recyclerPloatList = view.findViewById(R.id.recycler_view);
      no_records_text_view=(TextView)view.findViewById(R.id.no_records_text_view);
        recyclerPloatList.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerPloatList.getRecycledViewPool().clear();

        if (isDialogHided) {
            isDialogHided = false;


            filterDialog.dismiss();
        }
        execute();



        return view;
    }

    private void Getblockproject(String ProjectId ,String BlockId) {

        ViewUtils.startProgressDialog(getActivity());

        Call<ApiResponse<List<PlotAvailable>>> getPlotAvailabilitylistfilterCall =
                apiHelper.getPlotAvailabilitylistfilter(ProjectId,BlockId);
        getPlotAvailabilitylistfilterCall.enqueue(new Callback<ApiResponse<List<PlotAvailable>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PlotAvailable>>> call,
                                   Response<ApiResponse<List<PlotAvailable>>> response) {

                ViewUtils.endProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            List<PlotAvailable> plotAvailableList = response.body().getBody();
                            plotAvailableListAdapter=new PlotAvailableListAdapter(getActivity(),listItemClickListener,plotAvailableList);
                            recyclerPloatList.setAdapter(plotAvailableListAdapter);



                        }
                        else{

                            ViewUtils.showErrorDialog(getContext(), response.body().getMessage(),
                                    new DialogActionCallback() {
                                        @Override
                                        public void okAction() {
                                            ViewUtils.showToast(getString(R.string.No_Record_Available));

                                        }
                                    });


                        }

                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PlotAvailable>>> call, Throwable t) {
                if (!call.isCanceled()) {

                    ViewUtils.endProgressDialog();

                }
                t.printStackTrace();
            }
        });
    }




    private void GetProject() {
        Call<ApiResponse<List<GetProject>>> getProjectlistCall =
                apiHelper.getProject();
        getProjectlistCall.enqueue(new Callback<ApiResponse<List<GetProject>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<GetProject>>> call,
                                   Response<ApiResponse<List<GetProject>>> response) {


                if (response.isSuccessful()) {
                    final List<GetProject> getProjectList = new ArrayList<>();
                    GetProject getProject = new GetProject();
                    getProject.setStrProjectName("--Select Project--");
                    getProject.setIntProjectId(0);
                    getProjectList.add(getProject);
                    getProjectList.addAll(response.body().getBody());
                    ArrayAdapter<GetProject> adapter = new ArrayAdapter<>(getContext(),
                            R.layout.simple_spinner_item, getProjectList);
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    project_name_spinner.setAdapter(adapter);
                    project_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String projectName = parent.getSelectedItem().toString();
                            if (!projectName.equals("--Select Project--")) {
                                int ids=getProjectList.get(position).getIntProjectId();
                                GetBlock(String.valueOf(ids));
                                ProjectId=String.valueOf(ids);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });

                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<GetProject>>> call, Throwable t) {
                if (!call.isCanceled()) {

                }
                t.printStackTrace();
            }
        });
    }

    private void GetBlock(String ProjectId) {

        Call<ApiResponse<List<GetBlock>>> getBlocklistCall =
                apiHelper.getBlocks(ProjectId);
        getBlocklistCall.enqueue(new Callback<ApiResponse<List<GetBlock>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<GetBlock>>> call,
                                   Response<ApiResponse<List<GetBlock>>> response) {


                if (response.isSuccessful()) {
                    final List<GetBlock> getblockList = new ArrayList<>();
                    GetBlock getBlock = new GetBlock();
                    getBlock.setStrBlockName("--Select Block--");
                    getBlock.setIntBlockId(0);
                    getblockList.add(getBlock);
                    getblockList.addAll(response.body().getBody());
                    ArrayAdapter<GetBlock> adapter = new ArrayAdapter<>(getContext(),
                            R.layout.simple_spinner_item, getblockList);
                    adapter.setDropDownViewResource(R.layout.simple_spinner_dropdown_item);
                    block_name_spinner.setAdapter(adapter);
                    block_name_spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            String projectName = parent.getSelectedItem().toString();
                            if (!projectName.equals("--Select Project--")) {
                                int ids=getblockList.get(position).getIntBlockId();
                                BlockId=String.valueOf(ids);
                            }
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });


                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<GetBlock>>> call, Throwable t) {
                if (!call.isCanceled()) {

                }
                t.printStackTrace();
            }
        });
    }


    private void execute() {

        ViewUtils.startProgressDialog(getActivity());

        Call<ApiResponse<List<PlotAvailable>>> getPlotAvailableCall =
                apiHelper.getPlotAvailable();
        getPlotAvailableCall.enqueue(new Callback<ApiResponse<List<PlotAvailable>>>() {
            @Override
            public void onResponse(Call<ApiResponse<List<PlotAvailable>>> call,
                                   Response<ApiResponse<List<PlotAvailable>>> response) {

                ViewUtils.endProgressDialog();

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        if (response.body().getResponse().equalsIgnoreCase("Success")) {
                            List<PlotAvailable> plotAvailableList = response.body().getBody();

                            plotAvailableListAdapter = new PlotAvailableListAdapter(getActivity(), listItemClickListener,
                                    response.body().getBody());
                            recyclerPloatList.setAdapter(plotAvailableListAdapter);


                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<ApiResponse<List<PlotAvailable>>> call, Throwable t) {
                if (!call.isCanceled()) {

                }
                t.printStackTrace();
            }
        });
    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_filter, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    //On click of option menu
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (id == R.id.action_filter) {
            if (isDialogHided) {
                filterDialog.show();
                isDialogHided = false;
            } else {
                showFilterDialog();
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    private void showFilterDialog() {
        filterDialog = new Dialog(getContext());
        final DialogPlotFilterBinding binding = DataBindingUtil.inflate(LayoutInflater.from(getContext()),
                R.layout.dialog_plot_filter, null, false);
        filterDialog.setContentView(binding.getRoot());
        filterDialog.setCancelable(true);
        filterDialog.setCanceledOnTouchOutside(true);
        project_name_spinner = (Spinner) filterDialog.findViewById(R.id.project_name_spinner);
        block_name_spinner=(Spinner)filterDialog.findViewById(R.id.block_name_spinner);

        ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
        InsetDrawable inset = new InsetDrawable(back, 70);
        Window window = filterDialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(inset);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
            window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
        }
        filterDialog.show();
        GetProject();

        binding.GetPlot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                filterDialog.hide();
                isDialogHided = true;
               Getblockproject(ProjectId,BlockId);


            }
        });
    }


}



