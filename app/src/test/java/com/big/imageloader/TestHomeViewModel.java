package com.big.imageloader;


import android.content.SharedPreferences;

import androidx.arch.core.executor.testing.InstantTaskExecutorRule;

import com.big.imageloader.data.remote.NetworkService;
import com.big.imageloader.ui.viewmodel.HomeViewModel;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import io.reactivex.disposables.CompositeDisposable;
import kotlin.jvm.JvmField;

import static android.content.SharedPreferences.Editor;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TestHomeViewModel {

    @Rule
    @JvmField
    public TestRule rule = new InstantTaskExecutorRule();

    @Mock
    @JvmField
    public NetworkService networkService = null;

    @Mock
    @JvmField
    public SharedPreferences sharedPreferences = null;

    private CompositeDisposable compositeDisposable = null;

    private HomeViewModel homeViewModel = null;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        compositeDisposable = new CompositeDisposable();
        homeViewModel =
                new HomeViewModel(compositeDisposable, networkService, sharedPreferences);
    }

    @Test
    /*
     * Test to check if created items are null or not
     */
    public void _01TestNonNull() {
        assert (homeViewModel != null);
        assert (networkService != null);
        assert (sharedPreferences != null);
    }

    @Test
    /*
     * Test to check if shared preference don't contain any item and we want to save a city
     */
    public void _02TestSaveDataInLocal() {
        //Mocking
        Editor editor = Mockito.mock(Editor.class);
        when(sharedPreferences.edit()).thenReturn(editor);
        when(editor.putString(anyString(), anyString())).thenReturn(editor);
        doNothing().when(editor).apply();

        //Action
        homeViewModel.saveDataInLocal(SAMPLE_KEY_WORD, SAMPLE_RESPONSE);

        //Verify
        verify(editor, times(1)).putString(anyString(), anyString());
        verify(editor, times(1)).apply();
    }

    @Test
    /*
     * Test to check checkIfDataIsInLocal(...) method
     */
    public void _03TestCheckIfDataIsInLocal_ForEmptyString() {
        //Mocking
        when(sharedPreferences.contains(SAMPLE_KEY_WORD)).thenReturn(false);

        //Action & Verify
        assertEquals(homeViewModel.checkIfDataIsInLocal(SAMPLE_KEY_WORD), "");
    }

    @Test
    /*
     * Test to check checkIfDataIsInLocal(...) method
     */
    public void _04TestCheckIfDataIsInLocal_ForActualValue() {
        //Mocking
        when(sharedPreferences.contains(SAMPLE_KEY_WORD)).thenReturn(true);
        when(sharedPreferences.getString(SAMPLE_KEY_WORD, "")).thenReturn(SAMPLE_RESPONSE);

        //Action & Verify
        assertEquals(homeViewModel.checkIfDataIsInLocal(SAMPLE_KEY_WORD), SAMPLE_RESPONSE);
    }

    @Test
    /*
     * Test to check if search results are saved in sharedPreference
     * And returns the search result
     */
    public void _05TestNetworkQuery() {
        int pageNumber = 1;
        int itemsPerPage = 20;

        //Mocking
        when(sharedPreferences.contains(SAMPLE_KEY_WORD + ";" + pageNumber)).thenReturn(true);
        when(sharedPreferences.getString(SAMPLE_KEY_WORD + ";" + pageNumber, "")).thenReturn(SAMPLE_RESPONSE);

        // Action
        homeViewModel.getSearchResult(SAMPLE_KEY_WORD, pageNumber, itemsPerPage);

        // Verify
        verify(networkService, never()).searchImages(anyString(), anyString(),
                anyBoolean(), anyInt(), anyInt(), anyString(), anyBoolean());
    }

    @After
    public void tearDown() {
        compositeDisposable = null;
        homeViewModel = null;
        networkService = null;
        sharedPreferences = null;
    }

    private static final String SAMPLE_KEY_WORD = "WORKING";

    private static final String SAMPLE_RESPONSE = "{\n" +
            "  \"_type\": \"images\",\n" +
            "  \"totalCount\": 2618,\n" +
            "  \"value\": [\n" +
            "    {\n" +
            "      \"url\": \"https://static.independent.co.uk/s3fs-public/thumbnails/image/2020/03/23/11/working-from-home.jpg\",\n" +
            "      \"height\": 1591,\n" +
            "      \"width\": 2121,\n" +
            "      \"thumbnail\": \"https://rapidapi.contextualwebsearch.com/api/thumbnail/get?value=6519976189241034072\",\n" +
            "      \"thumbnailHeight\": 222,\n" +
            "      \"thumbnailWidth\": 295,\n" +
            "      \"base64Encoding\": null,\n" +
            "      \"name\": \"\",\n" +
            "      \"title\": \"Working from home: How to avoid burnout | The Independent\",\n" +
            "      \"imageWebSearchUrl\": \"https://contextualwebsearch.com/search/working/images\"\n" +
            "    }\n" +
            "  ]\n" +
            "}";
}
