package deepshikha.jangidyahoo.finalyearproject;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import deepshikha.jangidyahoo.finalyearproject.Adapter.CallLogAdapter;
import deepshikha.jangidyahoo.finalyearproject.model.CallLogItem;

public class call_logFragment extends Fragment {

    private RecyclerView recyclerView;
    private CallLogAdapter adapter;

    public void CallLogsFragment() {
        // Required empty public constructor
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_call_log, container, false);

        recyclerView = view.findViewById(R.id.RV_callLog);

        // Set layout manager
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        // Set adapter
        adapter = new CallLogAdapter();
        recyclerView.setAdapter(adapter);

        // Add sample data for testing
        List<CallLogItem> callLogItems = generateSampleData();
        adapter.setCallLogItems(callLogItems);
        return view;
    }
    private List<CallLogItem> generateSampleData() {
        List<CallLogItem> callLogItems = new ArrayList<>();
        callLogItems.add(new CallLogItem("John Doe", "1234567890", "2023-05-18 10:30 AM"));
        callLogItems.add(new CallLogItem("Jane Smith", "9876543210", "2023-05-17 3:45 PM"));
        // Add more sample data items as needed
        return callLogItems;
    }
}