package com.ntnu.wip.nabl.MVCControllers.ManageProjectClientInteraction.Register;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.ntnu.wip.nabl.Exceptions.CompanyNotFoundException;
import com.ntnu.wip.nabl.MVCView.ClientInput.ClientInputView;
import com.ntnu.wip.nabl.MVCView.ClientInput.IClientInputView;
import com.ntnu.wip.nabl.Models.Address;
import com.ntnu.wip.nabl.Models.Client;
import com.ntnu.wip.nabl.Models.ContactInformation;
import com.ntnu.wip.nabl.Network.FirestoreImpl.FireStoreClient;
import com.ntnu.wip.nabl.Network.IClient;
import com.ntnu.wip.nabl.R;
import com.ntnu.wip.nabl.Utils;

public class RegisterClientController extends Fragment implements IClientInputView.ClientInputListener {
    private ClientInputView mvcView;
    private Client newModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mvcView = new ClientInputView(inflater, null);
        mvcView.registerListener(this);

        return mvcView.getRootView();
    }

    @Override
    public void getWarningDrawable() {
        final Drawable icon = getResources().getDrawable(R.drawable.ic_error_black_18dp);
        mvcView.setEditWarning(icon);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState){
        super.onActivityCreated(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.register_button, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.register:
                if(mvcView.checkValidity()) {
                    registerModel();
                    finishFragment();
                } else {
                    Toast.makeText(getContext(), getString(R.string.inputInvalid), Toast.LENGTH_SHORT).show();
                }
                break;
            default: return super.onOptionsItemSelected(item);
        }

        return super.onOptionsItemSelected(item);
    }

    private void registerModel(){
        newModel = new Client();

        getCoreInformation();
        getContactInformation();
        getAddressInformation();

        try {
            saveModel();
        } catch (CompanyNotFoundException e) {
            Toast.makeText(getContext(), (R.string.workspaceNotSat), Toast.LENGTH_SHORT).show();
        }
    }

    private void saveModel() throws CompanyNotFoundException {
        IClient client = new FireStoreClient(getContext());
        client.writeNewClient(newModel);
    }

    private void getCoreInformation(){
        newModel.setName(mvcView.getName());
    }

    private void getContactInformation(){
        ContactInformation contactInformation = new ContactInformation();

        contactInformation.setEmail(mvcView.getEmail());
        contactInformation.setPhoneNumber(Integer.parseInt(mvcView.getPhone()));

        newModel.setContactInformation(contactInformation);
    }

    private void getAddressInformation(){
        Address address = new Address();

        address.setStreet(mvcView.getStreet());
        address.setNumber(Integer.parseInt(mvcView.getHouseNumber()));
        address.setZipcode(Integer.parseInt(mvcView.getZipcode()));
        address.setCity(mvcView.getCity());

        newModel.setAddress(address);
    }

    private void finishFragment(){
        getActivity().onBackPressed();
    }
}
