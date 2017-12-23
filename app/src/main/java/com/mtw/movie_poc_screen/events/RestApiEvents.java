package com.mtw.movie_poc_screen.events;

import java.util.List;

import com.mtw.movie_poc_screen.data.vo.MoviePopularVO;

/**
 * Created by Aspire-V5 on 12/6/2017.
 */

public class RestApiEvents {

    public static class ErrorInvokingAPIEvent {
        private String errorMsg;

        public ErrorInvokingAPIEvent(String errorMsg) {
            this.errorMsg = errorMsg;
        }

        public String getErrorMsg() {
            return errorMsg;
        }
    }

    public static class MoviesDataLoadedEvent {
        private int loadedPageIndex;
        private List<MoviePopularVO> loadedMovies;

        public MoviesDataLoadedEvent(int loadedPageIndex, List<MoviePopularVO> loadedMovies) {
            this.loadedPageIndex = loadedPageIndex;
            this.loadedMovies = loadedMovies;
        }

        public int getLoadedPageIndex() {
            return loadedPageIndex;
        }

        public List<MoviePopularVO> getLoadedMovies() {
            return loadedMovies;
        }
    }
}
