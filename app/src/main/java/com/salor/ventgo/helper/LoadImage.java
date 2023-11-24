package com.salor.ventgo.helper;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.tuyenmonkey.mkloader.MKLoader;

import java.io.File;


public class LoadImage {
    private Context context;

    public LoadImage(Context context) {
        this.context = context;
    }

    public void LoadImagePicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }

                            Picasso
                                    .with(context)
                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09");
                        }
                    });
        }
    }

    public void LoadImagePicassoMkLoader(String uLoad, ImageView target, final MKLoader mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {

            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            mDialog.setVisibility(View.GONE);
                        }

                        @Override
                        public void onError() {
                            mDialog.setVisibility(View.GONE);
                        }
                    });
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }

                            Picasso
                                    .with(context)
                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09");
                        }
                    });
        }
    }

//    public void LoadImagePicassoWithSkeletonGroup(String uLoad, ImageView target, final SkeletonGroup skeletonGroup) {
//        String load = uLoad.replace(" ", "%20");
//
//        skeletonGroup.startAnimation();
//        skeletonGroup.setShowSkeleton(true);
//
//        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
//
//            skeletonGroup.finishAnimation();
//            skeletonGroup.setShowSkeleton(false);
//
//            Picasso
//                    .with(context)
//                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
//                    
//                    .into(target);
//        } else {
//            Picasso
//                    .with(context)
//                    .load(load)
//                    
//                    .into(target, new Callback() {
//                        @Override
//                        public void onSuccess() {
//
//                            skeletonGroup.finishAnimation();
//                            skeletonGroup.setShowSkeleton(false);
//
//                        }
//
//                        @Override
//                        public void onError() {
//
//                            skeletonGroup.finishAnimation();
//                            skeletonGroup.setShowSkeleton(false);
//
//                            Picasso
//                                    .with(context)
//                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
//                                    ;
//                        }
//                    });
//        }
//    }

    public void LoadImagePicassoWithOutPlaceHolder(String uLoad, ImageView target, final ProgressBar mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")

                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }

                            Picasso
                                    .with(context)
                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09");
                        }
                    });
        }
    }

    public void LoadImagePicassoWithOutProgressbar(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {

            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .into(target);
        } else {

            Picasso
                    .with(context)
                    .load(load)
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {

                            Picasso
                                    .with(context)
                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09");
                        }
                    });
        }
    }


    public void LoadImagePicassoResize(String uLoad, final ImageView target, final ProgressBar mDialog, final int width, final int height) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .resize(width, height)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .resize(width, height)
                    .centerCrop()

                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                                Picasso
                                        .with(context)
                                        .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                                        .transform(new RoundedTransform(24, 0))

                                        .resize(width, height)
                                        .centerCrop()
                                        .into(target);
                            }
                        }
                    });
        }
    }

    public void LoadImagePicassoResizeMkLoader(String uLoad, final ImageView target, final MKLoader mDialog, final int width, final int height) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .resize(width, height)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .resize(width, height)
                    .centerCrop()

                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                                Picasso
                                        .with(context)
                                        .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                                        .transform(new RoundedTransform(24, 0))

                                        .resize(width, height)
                                        .centerCrop()
                                        .into(target);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedNoProgressDialogPicasso(String uLoad, final ImageView target, final int widht, final int height) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equals("null")) {
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .transform(new RoundedTransform(30, 0))
                    .resize(widht, height)
                    .centerCrop()
                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(30, 0))

                    .resize(widht, height)
                    .centerCrop()
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {

                        }

                        @Override
                        public void onError() {
                            Picasso
                                    .with(context)
                                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                                    .transform(new RoundedTransform(30, 0))
                                    .resize(widht, height)
                                    .centerCrop()
                                    .into(target);
                        }
                    });
        }
    }

    public void LoadImageCirclePicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            mDialog.setVisibility(View.GONE);
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .transform(new CircleTransform())

                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new CircleTransform())

                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageCircleNoProgressbarPicasso(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty() || load.equalsIgnoreCase("null")) {
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .transform(new CircleTransform())

                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new CircleTransform())

                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        }
    }

    public void LoadImageRoundedPicasso(String uLoad, ImageView target, final ProgressBar mDialog) {
        if (uLoad.equalsIgnoreCase("") || uLoad.isEmpty() || uLoad.equalsIgnoreCase("null")) {
            if (mDialog != null) {
                mDialog.setVisibility(View.GONE);
            }
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .transform(new RoundedTransform(24, 0))

                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            String load = uLoad.replace(" ", "%20");
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(24, 0))

                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedFilePicasso(File load, ImageView target, final ProgressBar mDialog) {

        if (load == null) {
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .transform(new RoundedTransform(24, 0))

                    .resize(300, 300)
                    .centerCrop()
                    .into(target);
        } else {
            mDialog.setVisibility(View.VISIBLE);
            Picasso
                    .with(context)
                    .load(load)
                    .transform(new RoundedTransform(24, 0))

                    .resize(300, 300)
                    .centerCrop()
                    .into(target, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError() {
                            if (mDialog != null) {
                                mDialog.setVisibility(View.GONE);
                            }
                        }
                    });
        }
    }

    public void LoadImageRoundedDrawablePicasso(ImageView target) {
        Picasso
                .with(context)
                .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                .transform(new RoundedTransform(24, 0))

                .resize(300, 300)
                .centerCrop()
                .into(target);
    }

    public void LoadImageNoProgressPicasso(String uLoad, ImageView target) {
        String load = uLoad.replace(" ", "%20");
        if (load.equalsIgnoreCase("") || load.isEmpty()) {
            Picasso
                    .with(context)
                    .load("https://firebasestorage.googleapis.com/v0/b/modul-74a9a.appspot.com/o/no_image.jpg?alt=media&token=68c2e66e-f14b-42bc-bdae-087346ba1d09")
                    .centerCrop()
                    .resize(300, 300)

                    .into(target);
        } else {
            Picasso
                    .with(context)
                    .load(load)

                    .into(target);
        }
    }

}
