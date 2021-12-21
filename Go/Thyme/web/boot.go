package web

import (
	auth1 "Thyme/web/auth"
	"Thyme/web/extension"
	"Thyme/web/model"
	"github.com/gin-gonic/gin"
)

func Init() (*gin.Engine, error) {
	router := gin.Default()
	tableInit()
	routerInit(router)
	return router, nil
}

func routerInit(router *gin.Engine) {

	auth := router.Group("/auth")

	{
		auth.POST("/register", auth1.Register)
		auth.POST("/login", auth1.Login)
		auth.POST("/", func(ctx *gin.Context) {
			token :=ctx.GetHeader("token")
			check := extension.ParseToken(token)
			ctx.JSON(200,check)
		})
	}
}

func tableInit() {
	_ = extension.DB.AutoMigrate(&model.User{})
}
