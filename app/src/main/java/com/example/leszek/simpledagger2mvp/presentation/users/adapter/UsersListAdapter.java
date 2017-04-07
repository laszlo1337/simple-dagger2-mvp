package com.example.leszek.simpledagger2mvp.presentation.users.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.leszek.simpledagger2mvp.R;
import com.example.leszek.simpledagger2mvp.domain.model.User;
import com.example.leszek.simpledagger2mvp.presentation.users.UserModel;
import com.example.leszek.simpledagger2mvp.presentation.users.UsersView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Leszek Janiszewski
 */

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.UserViewHolder> {

    UsersView usersView;
    private List<UserModel> users;
    private int lastPosition;

    public UsersListAdapter() {
        this.users = new ArrayList<>();
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_element, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        final UserModel user = users.get(position);
        Glide.with(holder.avatar.getContext()).load(user.getAvatarUrl()).into(holder.avatar);
        holder.login.setText(user.getLogin());
        holder.id.setText(String.valueOf(user.getId()));
        holder.item.setOnClickListener(v -> usersView.onClickListItem(user.getLogin()));
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public void setUsers(List<UserModel> users) {
        if (users != null) {
            this.users = users;
            this.lastPosition = users.size();
            notifyDataSetChanged();
        }
    }

    public void update(List<UserModel> users) {
        if (users != null) {
            this.users.addAll(users);
            notifyItemRangeInserted(lastPosition, users.size());
            lastPosition = this.users.size();
        }
    }

    static class UserViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.user_avatar)
        ImageView avatar;
        @BindView(R.id.user_login)
        TextView login;
        @BindView(R.id.user_id)
        TextView id;
        View item;

        public UserViewHolder(View view) {
            super(view);
            item = view;
            ButterKnife.bind(this, view);
        }
    }

    public void setOnClickListener(UsersView usersView){
        this.usersView = usersView;
    }

}
