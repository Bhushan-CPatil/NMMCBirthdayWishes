package com.ornettech.qcandbirthdaywishes.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.core.view.MenuItemCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.ornettech.qcandbirthdaywishes.R;
import com.ornettech.qcandbirthdaywishes.adapter.AdapterCorporatorList;
import com.ornettech.qcandbirthdaywishes.api.RetrofitClient;
import com.ornettech.qcandbirthdaywishes.listener.GalleryListener;
import com.ornettech.qcandbirthdaywishes.model.CorporatorsElectionWiseItem;
import com.ornettech.qcandbirthdaywishes.model.CorporatosResponse;
import com.ornettech.qcandbirthdaywishes.model.ElectionListItem;
import com.ornettech.qcandbirthdaywishes.model.SpinnerResponse;
import com.ornettech.qcandbirthdaywishes.utility.CheckConnection;
import com.ornettech.qcandbirthdaywishes.utility.SharedPrefManager;
import com.ornettech.qcandbirthdaywishes.utility.Transalator;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WardWiseBirthdayImageList extends AppCompatActivity implements GalleryListener {

    Spinner spnelecname;
    Button submit;
    TextView error;
    RecyclerView corporatorlist;
    public List<ElectionListItem> electionlist = new ArrayList<>();
    public List<CorporatorsElectionWiseItem> corporatorsElectionWiseItems = new ArrayList<>();
    public List<String> arrayList = new ArrayList<>();
    public AdapterCorporatorList mAdapter;
    private Uri fileUri;
    String op="",electionname,corpcd;
    int wardno = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ward_wise_birthday_image_list);
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        getSupportActionBar().setTitle(Html.fromHtml("<font color='#FFFFFF'>Corporators List</font>"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back_black);
        spnelecname = findViewById(R.id.spnelecname);
        submit = findViewById(R.id.submit);
        error = findViewById(R.id.error);
        corporatorlist = findViewById(R.id.corporatorlist);
        error.setVisibility(View.GONE);
        corporatorlist.setVisibility(View.GONE);

        setAdapter();
        fetchElectionNames();

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (new CheckConnection(WardWiseBirthdayImageList.this).isNetworkConnected()) {
                    submitMethod();
                }else {
                    Toast.makeText(WardWiseBirthdayImageList.this,
                            "No Active Internet Connection!",
                            Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void submitMethod() {
        corporatorsElectionWiseItems.clear();
        final ProgressDialog progressBar = new ProgressDialog(WardWiseBirthdayImageList.this);
        progressBar.setCancelable(false);
        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar.setMessage("Please wait ......");
        progressBar.show();

        Call<CorporatosResponse> call1 = RetrofitClient
                .getInstance().getApi().getCorporatorsList(spnelecname.getSelectedItem().toString().trim());
        call1.enqueue(new Callback<CorporatosResponse>() {
            @Override
            public void onResponse(Call<CorporatosResponse> call1, Response<CorporatosResponse> response) {
                CorporatosResponse res = response.body();
                progressBar.dismiss();
                if(res.getCorporatorsElectionWise() != null && res.getCorporatorsElectionWise().size()>0){
                    corporatorsElectionWiseItems = res.getCorporatorsElectionWise();
                    error.setVisibility(View.GONE);
                    corporatorlist.setVisibility(View.VISIBLE);
                    mAdapter = new AdapterCorporatorList(WardWiseBirthdayImageList.this, corporatorsElectionWiseItems,spnelecname.getSelectedItem().toString().trim());
                    corporatorlist.setNestedScrollingEnabled(false);
                    corporatorlist.setLayoutManager(new LinearLayoutManager(WardWiseBirthdayImageList.this));
                    mAdapter.onItemClick(WardWiseBirthdayImageList.this);
                    corporatorlist.setAdapter(mAdapter);
                    corporatorlist.getAdapter().notifyDataSetChanged();
                }else{
                    error.setText("List is empty !");
                    error.setVisibility(View.VISIBLE);
                    corporatorlist.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<CorporatosResponse> call1, Throwable t) {
                progressBar.dismiss();
                error.setText("Failed to fetch data !");
                error.setVisibility(View.VISIBLE);
                corporatorlist.setVisibility(View.GONE);
            }
        });
    }

    private void fetchElectionNames() {
        electionlist.clear();
        final ProgressDialog progressBar2 = new ProgressDialog(WardWiseBirthdayImageList.this);
        progressBar2.setCancelable(false);
        progressBar2.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressBar2.setMessage("Please wait ......");
        progressBar2.show();

        Call<SpinnerResponse> call1 = RetrofitClient
                .getInstance().getApi().spinnerOptionForRep(SharedPrefManager.getInstance(WardWiseBirthdayImageList.this).username(),SharedPrefManager.getInstance(WardWiseBirthdayImageList.this).getElectionName());
        call1.enqueue(new Callback<SpinnerResponse>() {
            @Override
            public void onResponse(Call<SpinnerResponse> call1, Response<SpinnerResponse> response) {
                SpinnerResponse res = response.body();
                progressBar2.dismiss();
                if(res.getElectionList().size()>0) {
                    electionlist = res.getElectionList();
                    for (int i=0;i<electionlist.size();i++){
                        arrayList.add(electionlist.get(i).getElectionName());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(WardWiseBirthdayImageList.this, android.R.layout.simple_spinner_item, arrayList);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    spnelecname.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<SpinnerResponse> call1, Throwable t) {
                progressBar2.dismiss();
                Toast.makeText(WardWiseBirthdayImageList.this, "Failed to fetch data !", Toast.LENGTH_LONG).show();
            }
        });
    }

    private void setAdapter() {
        corporatorlist.setNestedScrollingEnabled(false);
        corporatorlist.setLayoutManager(new LinearLayoutManager(WardWiseBirthdayImageList.this));
        corporatorlist.setAdapter(new RecyclerView.Adapter() {
                                @NonNull
                                @Override
                                public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                                    View view = LayoutInflater.from(WardWiseBirthdayImageList.this).inflate(R.layout.adapter_ward_wise_birthday_photo, viewGroup, false);
                                    InnerHolder holder = new InnerHolder(view);
                                    return holder;
                                }

                                @Override
                                public long getItemId(int position) {
                                    return position;
                                }

                                @Override
                                public int getItemViewType(int position) {
                                    return position;
                                }

                                @Override
                                public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, final int i) {
                                    final InnerHolder innerHolder = (InnerHolder) viewHolder;
                                    final CorporatorsElectionWiseItem model = corporatorsElectionWiseItems.get(i);

                                    if(model.getBirthDayWishImage()!=null && !model.getBirthDayWishImage().equalsIgnoreCase("")){
                                        innerHolder.llt.setBackgroundColor(Color.parseColor("#00C00C"));
                                        Glide.with(WardWiseBirthdayImageList.this).load(model.getBirthDayWishImage()).into(innerHolder.banner);
                                    }else{
                                        innerHolder.llt.setBackgroundColor(Color.parseColor("#FF5555"));
                                        innerHolder.banner.setImageDrawable(ContextCompat.getDrawable(WardWiseBirthdayImageList.this, R.drawable.ic_no_image_found));
                                    }

                                    if(model.getCorporatorNameMar()!=null && !model.getCorporatorNameMar().equalsIgnoreCase("")){
                                        innerHolder.corporatorname.setText(model.getCorporatorNameMar());
                                    }else{
                                        innerHolder.corporatorname.setText(model.getCorporatorName());
                                    }

                                    if(model.getDesignationNameMar()!=null && !model.getDesignationNameMar().equalsIgnoreCase("")){
                                        innerHolder.corpdesig.setText(model.getDesignationNameMar()+", प्रभाग क्र - "+Transalator.englishDigitToMarathiDigit(model.getWardNo()+""));
                                    }else{
                                        innerHolder.corpdesig.setText(model.getDesignationName()+", Ward No. - "+Transalator.englishDigitToMarathiDigit(model.getWardNo()+""));
                                    }

                                    if(model.getMobileNo1()!=null && !model.getMobileNo1().equalsIgnoreCase("")){
                                        innerHolder.corpmob.setText(model.getMobileNo1());
                                    }else{
                                        innerHolder.corpmob.setText("");
                                    }

                                    if(model.getParty()!=null && !model.getParty().equalsIgnoreCase("")){
                                        innerHolder.corpparty.setText(model.getParty());
                                    }else{
                                        innerHolder.corpparty.setText("");
                                    }

                                    innerHolder.itemView.setTag(i);
                                    innerHolder.itemView.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {

                                        }
                                    });
                                }

                                @Override
                                public int getItemCount() {
                                    //return fullsumm.size();
                                    return corporatorsElectionWiseItems.size();
                                }

                                class InnerHolder extends RecyclerView.ViewHolder {
                                    TextView corporatorname, corpdesig, corpmob, corpparty;
                                    LinearLayout llt;
                                    ImageView banner;

                                    public InnerHolder(@NonNull View itemView) {
                                        super(itemView);
                                        llt = itemView.findViewById(R.id.llt);
                                        banner = itemView.findViewById(R.id.banner);
                                        corporatorname = itemView.findViewById(R.id.corporatorname);
                                        corpdesig = itemView.findViewById(R.id.corpdesig);
                                        corpmob = itemView.findViewById(R.id.corpmob);
                                        corpparty = itemView.findViewById(R.id.corpparty);
                                    }
                                }
                            }
        );
    }

    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem search = menu.findItem(R.id.action_search);
        MenuItem report = menu.findItem(R.id.action_report);
        MenuItem report2 = menu.findItem(R.id.action_report2);
        MenuItem logout = menu.findItem(R.id.action_logout);

        report.setVisible(false);
        report2.setVisible(false);

        logout.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                SharedPrefManager.getInstance(WardWiseBirthdayImageList.this).clear();
                Intent intent = new Intent(WardWiseBirthdayImageList.this, LoginAcivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                return false;
            }
        });

        SearchView searchView = (SearchView) MenuItemCompat.getActionView(search);
        searchView.setQueryHint("Search Corporators");
        search(searchView);



        return true;
    }

    private void search(SearchView searchView) {
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                mAdapter.getFilter().filter(newText);
                return true;
            }
        });
    }

    private void getImageFromGallery() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto, 99);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            onBackPressed();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(int position, int ward_no, String option, String electnm, String cocd) {
        op = option;
        wardno = ward_no;
        electionname = electnm;
        corpcd = cocd;
        getImageFromGallery();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putParcelable("file_uri", fileUri);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // get the file url
        fileUri = savedInstanceState.getParcelable("file_uri");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // if the result is capturing Image
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 99) {
            if (resultCode == RESULT_OK) {
                fileUri = data.getData();
                fileUri = Uri.parse(getRealPathFromURI(fileUri));
                launchUploadActivity(fileUri);
            } else if (requestCode == RESULT_CANCELED) {
                Toast.makeText(getApplicationContext(),
                        "User cancelled image selection !", Toast.LENGTH_SHORT)
                        .show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Sorry! Failed to get image !", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void launchUploadActivity(Uri fileUriC) {
        Intent i = new Intent(WardWiseBirthdayImageList.this, BirthDayPhotoUploadActivity.class);
        i.putExtra("fileUri", fileUriC.getPath());
        i.putExtra("options", op);
        i.putExtra("wardno", Integer.toString(wardno));
        i.putExtra("electionname", electionname);
        i.putExtra("corpcd", corpcd);
        startActivity(i);
    }

    public String getRealPathFromURI(Uri contentUri) {

        // can post image
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(contentUri,
                proj, // Which columns to return
                null,       // WHERE clause; which rows to return (all rows)
                null,       // WHERE clause selection arguments (none)
                null); // Order-by clause (ascending by name)
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        return cursor.getString(column_index);
    }
}
