import com.plub.data.base.DataDto
import com.plub.data.model.categorylistdata.CategoryListData

data class CategoryListResponse(
    //val statusCode : Int,
    val data : CategoryListData
) : DataDto