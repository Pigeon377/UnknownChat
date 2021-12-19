package web

import "github.com/gin-gonic/gin"

func Init() (*gin.Engine,error) {
	router := gin.Default()

	auth := router.Group("/auth")

	{
		auth.POST("/register",nil)
	}


	return router,nil
}
